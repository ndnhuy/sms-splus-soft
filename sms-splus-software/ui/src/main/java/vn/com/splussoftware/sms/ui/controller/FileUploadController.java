package vn.com.splussoftware.sms.ui.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController {
	@Autowired
	ServletContext context;

	@RequestMapping(value = "/uploadToTemp", method = RequestMethod.POST)
	String uploadMultipleFileHandler(@RequestParam("name") String[] names,
			@RequestParam("file") MultipartFile[] files) {

		if (files.length != names.length)
			return "Mandatory information missing";

		String message = "";
		for (int i = 0; i < files.length; i++) {

			MultipartFile file = files[i];
			String name = names[i];
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = context.getContextPath();
				File dir = new File(rootPath + File.separator + "username" + File.separator + "temps");
				if (!dir.exists()) {
					dir.mkdirs();
				}

				System.out.println(dir.getAbsolutePath());

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				message = message + "You successfully uploaded file=" + name;
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}

		}
		return message;
	}

	@RequestMapping(value = "/moveToUpload", method = RequestMethod.POST)
	String moveToUpload() {
		String rootPath = context.getContextPath();
		File dir = new File(rootPath + File.separator + "username" + File.separator + "temps");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File des = new File(rootPath + File.separator + "username" + File.separator + "uploads");
		if (!des.exists()) {
			des.mkdirs();
		}
		try {
			File file = new File(dir.getAbsolutePath());
			if (file.isDirectory()) {
				File[] content = file.listFiles();
				for (File aFile : content) {
					System.out.println("File: " + aFile.getName());
					FileUtils.copyFileToDirectory(aFile,des);
					FileUtils.forceDelete(aFile);
					

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "xong";

	}

	public static void deleteExpired(String dir) {
		try {
			File file = new File(dir);
			if (file.isDirectory()) {
				File[] content = file.listFiles();
				for (File aFile : content) {
					System.out.println("File: " + aFile.getName());
				}
			}
			long diff = new Date().getTime() - file.lastModified();
			if (diff > 60 * 1000) {
				System.out.println(file.getName() + " is deleted!");
			} else {
				System.out.println("Delete operation is failed.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
