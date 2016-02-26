package vn.com.splussoftware.sms.ui.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.annotation.MultipartConfig;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vn.com.splussoftware.sms.model.constant.FileUploadConstant;
import vn.com.splussoftware.sms.model.entity.FileUploadEntity;
import vn.com.splussoftware.sms.model.exception.ValidatorErrorModelException;
import vn.com.splussoftware.sms.utils.service.FileUploadService;
import vn.com.splussoftware.sms.utils.validator.FileUploadValidator;

@RestController
@MultipartConfig(maxFileSize = FileUploadConstant.MAX_FILE_SIZE, maxRequestSize = FileUploadConstant.MAX_UPLOAD_SIZE)
public class FileUploadController implements Runnable {

	// BuuPV : Storing file name , for tracking which file will be move to
	// uploads
	List<String> fileName = new ArrayList<String>();

	@Autowired
	FileUploadService uploadService;

	// BuuPV: Upload file to tmp folder
	@RequestMapping(value = "/uploadToTemp", method = RequestMethod.POST)
	String uploadMultipleFileHandler(@RequestParam("file") MultipartFile[] files) {
		try {
			// Reset list name
			fileName.clear();

			// BuuPV: List storing all invalid file's info
			List<ValidatorErrorModelException> errorList = new ArrayList<ValidatorErrorModelException>();

			String message = "";
			for (int i = 0; i < files.length; i++) {

				// BuuPV: Creating the directory to store temps file
				File dir = new File("username" + File.separator + "temps");
				if (!dir.exists()) {
					dir.mkdirs();
				}
				MultipartFile file = files[i];

				// BuuPV: variable for checking invalid file, true if valid and
				// false if invalid
				boolean check = FileUploadValidator.checkUploadFile(errorList, file);
				if (!check) {
					continue;
				}

				// BuuPV: Rename file if duplicate on temps
				
				String name = FileUploadController.renameDuplicates(
						file.getOriginalFilename(),
						dir.getAbsolutePath());

				System.out.println(name);
				fileName.add(name);
				try {
					byte[] bytes = file.getBytes();
					// BuuPV: Create the file on server
					File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();

					message = message + "Your file has been successfully uploaded";
				} catch (Exception e) {
					return "You failed to upload " + name + " => " + e.getMessage();
				}

			}

			for (int i = 0; i < errorList.size(); i++) {
				System.out.println(errorList.get(i).getErrorMessage());
			}
		} catch (IllegalStateException e) {
			return "file bu wa";
		}

		return "det";
	}

	// BuuPV: Move uploaded temp file to uploads folder
	@RequestMapping(value = "/moveToUpload", method = RequestMethod.POST)
	String moveToUpload(@RequestParam("file") MultipartFile[] files) {

		// BuuPV: Get the temp directory
		File dir = new File("username" + File.separator + "temps");

		// BuuPV: Creating the directory to store uploads file
		File des = new File("username" + File.separator + "uploads");
		if (!des.exists()) {
			des.mkdirs();
		}

		try {
			// BuuPV: get temp folder
			File file = new File(dir.getAbsolutePath());
			if (file.isDirectory()) {
				File[] content = file.listFiles();

				//
				for (File aFile : content) {

					// BuuPV: Get the specific file based on the name list
					if (fileName.contains(aFile.getName())) {
						String newName = FileUploadController.renameDuplicates(aFile.getName(), des.getAbsolutePath());

						// BuuPV: Initiate Entity for Saving to DB
						FileUploadEntity newFile = new FileUploadEntity();
						newFile.setName(aFile.getName().substring(0, aFile.getName().lastIndexOf(".")));

						// BuuPV: Move file from temps to uploads
						aFile.renameTo(new File(des + File.separator + newName));

						File newAFile = new File(des + File.separator + newName);

						newFile.setSize(newAFile.length());
						newFile.setType(FilenameUtils.getExtension(newAFile.getAbsolutePath()));
						newFile.setCreatedTime(new Date());
						newFile.setPath(newAFile.getAbsolutePath());
						uploadService.createFile(newFile);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// BuuPV: clear file name list after moving
		fileName.clear();
		return "xong";

	}

	// BuuPV: Clean tmp folder
	public static void deleteExpired(String dir) {
		try {
			File file = new File(dir);
			if (file.isDirectory()) {
				File[] content = file.listFiles();
				for (File aFile : content) {
					long diff = new Date().getTime() - aFile.lastModified();
					if (diff > FileUploadConstant.EXPIRED_TIME) {
						aFile.delete();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// BuuPV: Thread for cleaning tmp folder periodically
	@Override
	public void run() {
		while (true) {
			FileUploadController.deleteExpired("username\\temps");
			try {
				Thread.sleep(FileUploadConstant.CLEAN_TMP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// BuuPV: Rename duplicated file
	public static String renameDuplicates(String fileName, String des) {
		int i = 1;
		File file = new File(des + File.separator + fileName);
		// BuuPV: check if new name still duplicates
		while (file.exists() && !file.isDirectory()) {

			file = new File(
					des + File.separator + fileName.substring(0, fileName.lastIndexOf(".")) + " (" + i + ")" + ".xls");
			i++;
		}
		return file.getName();
	}
}
