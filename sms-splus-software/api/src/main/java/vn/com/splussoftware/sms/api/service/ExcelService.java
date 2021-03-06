package vn.com.splussoftware.sms.api.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import vn.com.splussoftware.sms.model.entity.FileUploadEntity;
import vn.com.splussoftware.sms.model.exception.ExcelValidatorModel;
import vn.com.splussoftware.sms.model.exception.MatrixValidatorModel;
import vn.com.splussoftware.sms.model.exception.TableValidatorModel;
import vn.com.splussoftware.sms.model.exception.ValidatorErrorModelException;
import vn.com.splussoftware.sms.utils.service.FileUploadService;
import vn.com.splussoftware.sms.utils.service.jsonhandler.DataAdapter;
import vn.com.splussoftware.sms.utils.service.jsonhandler.DataObject;
import vn.com.splussoftware.sms.utils.service.jsonhandler.ElementData;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Individual;
import vn.com.splussoftware.sms.utils.service.jsonhandler.IndividualData;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Matrix;
import vn.com.splussoftware.sms.utils.service.jsonhandler.MatrixColumnRow;
import vn.com.splussoftware.sms.utils.service.jsonhandler.MatrixData;
import vn.com.splussoftware.sms.utils.service.jsonhandler.MatrixValue;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Table;
import vn.com.splussoftware.sms.utils.service.jsonhandler.TableColumn;
import vn.com.splussoftware.sms.utils.service.jsonhandler.TableData;
import vn.com.splussoftware.sms.utils.service.jsonhandler.TableValue;
import vn.com.splussoftware.sms.utils.validator.ExcelValidator;

@Controller
public class ExcelService {
	@RequestMapping("/upload")
	public String upload() {
		return "upload";
	}

	@Autowired
	FileUploadService uploadService;

	@RequestMapping("/import")
	public String importExcel() throws IOException {
		// BuuPV: 20160129 list for storing
		List<ValidatorErrorModelException> errorList = new ArrayList<ValidatorErrorModelException>();
		List<ExcelValidatorModel> listValidateCell = new ArrayList<ExcelValidatorModel>();
		// FileInputStream file = new FileInputStream(new File("Json.xls"));
		FileUploadEntity newFile = uploadService.getFileById(19);
		FileInputStream file = new FileInputStream(new File(newFile.getPath()));
		// BuuPV: 20160121 [JSON String for test]
		String json = "{\"elements\":[{\"dataType\":\"individual\",\"name\":\"Link to Employees Picture Folder\",\"id\":-1,\"position\":1,\"data\":{\"value\":\"thu\",\"type\":\"select\",\"conditions\":{\"multiple\":false,\"data\":[{\"text\":\"Male\",\"value\":\"Male\"},{\"text\":\"Female\",\"value\":\"Female\"}]}}},{\"dataType\":\"individual\",\"name\":\"Link to Employees Picture Folder\",\"id\":-4,\"position\":4,\"data\":{\"value\":\"coi\",\"type\":\"select\",\"conditions\":{\"multiple\":false,\"data\":[{\"text\":\"Male\",\"value\":\"Male\"},{\"text\":\"Female\",\"value\":\"Female\"}]}}},{\"dataType\":\"table\",\"name\":\"Table Information Of Service\",\"id\":-5,\"position\":5,\"data\":{\"columns\":[{\"name\":\"No.\",\"type\":\"number\",\"id\":-1,\"position\":1,\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Fullname\",\"type\":\"text\",\"position\":2,\"id\":-2,\"conditions\":{\"required\":true,\"min-length\":5,\"max-length\":255}},{\"name\":\"Gender\",\"type\":\"select\",\"conditions\":{\"multiple\":false,\"data\":[{\"text\":\"Male\",\"value\":\"Male\"},{\"text\":\"Female\",\"value\":\"Female\"}]},\"position\":3,\"id\":-3},{\"name\":\"Parent Department\",\"position\":4,\"id\":-4,\"type\":\"text\",\"conditions\":{\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Child Department\",\"position\":5,\"id\":-5,\"type\":\"text\",\"conditions\":{\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Job\",\"position\":6,\"id\":-6,\"type\":\"text\",\"conditions\":{\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Contract Type\",\"type\":\"select\",\"id\":-7,\"position\":7,\"conditions\":{\"multiple\":false,\"data\":[{\"text\":\"Probation Contract\",\"value\":\"Probation Contract\"},{\"text\":\"Labor contract follow a certain job to have duration less than 12 months\",\"value\":\"Labor contract follow a certain job to have duration less than 12 months\"},{\"text\":\"Definite-term Labor contract\",\"value\":\"Definite-term Labor contract\"},{\"text\":\"Indefinite-term Labor contract\",\"value\":\"Indefinite-term Labor contract\"},{\"text\":\"Seasonal Contract\",\"value\":\"Seasonal Contract\"},{\"text\":\"Fresher Training Contract\",\"value\":\"Fresher Training Contract\"},{\"text\":\"Intern Training Contract\",\"value\":\"Intern Training Contract\"},{\"text\":\"Service Contract\",\"value\":\"Service Contract\"},{\"text\":\"Vender Contract\",\"value\":\"Vender Contract\"},{\"text\":\"Internship\",\"value\":\"Internship\"},{\"text\":\"Customer\",\"value\":\"Customer\"},{\"text\":\"Other\",\"value\":\"Other\"}],\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Start date\",\"type\":\"date\",\"position\":8,\"id\":-8,\"conditions\":{\"required\":true,\"format\":{\"text\":\"Please input dd/MM/yyyy\",\"regex\":\"dd/MM/yyyy\"}}},{\"name\":\"End date\",\"type\":\"date\",\"position\":9,\"id\":-9,\"conditions\":{\"required\":false,\"format\":{\"text\":\"Please input dd/MM/yyyy\",\"regex\":\"dd/MM/yyyy\"}}},{\"name\":\"Account\",\"type\":\"text\",\"position\":10,\"id\":-10,\"conditions\":{}},{\"name\":\"Add To Group\",\"type\":\"text\",\"position\":11,\"id\":-11,\"conditions\":{\"required\":true,\"min-length\":5,\"max-length\":1000}},{\"name\":\"Note\",\"type\":\"textarea\",\"position\":12,\"id\":-12,\"conditions\":{\"max-length\":1000}}],\"values\":[{\"columnId\":-3,\"row\":1,\"value\":\"cot 3 dong 1\"},{\"columnId\":-1,\"row\":2,\"value\":\"cot 1 dong 2\"},{\"columnId\":-2,\"row\":3,\"value\":\"cot 2 dong 3\"},{\"columnId\":-4,\"row\":3,\"value\":\"cot 4 dong 3\"},{\"columnId\":-5,\"row\":2,\"value\":\"cot 5 dong 2\"},{\"columnId\":-7,\"row\":1,\"value\":\"cot 7 dong 1\"},{\"columnId\":-7,\"row\":2,\"value\":\"cot 7 dong 2\"}]}},{\"dataType\":\"table\",\"name\":\"Table Information Of Ticket\",\"id\":-2,\"position\":2,\"data\":{\"columns\":[{\"name\":\"No.\",\"type\":\"number\",\"id\":-1,\"position\":1,\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Fullname\",\"type\":\"text\",\"position\":2,\"id\":-2,\"conditions\":{\"required\":true,\"min-length\":5,\"max-length\":255}},{\"name\":\"Gender\",\"type\":\"select\",\"conditions\":{\"multiple\":false,\"data\":[{\"text\":\"Male\",\"value\":\"Male\"},{\"text\":\"Female\",\"value\":\"Female\"}]},\"position\":3,\"id\":-3},{\"name\":\"Parent Department\",\"position\":4,\"id\":-4,\"type\":\"text\",\"conditions\":{\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Child Department\",\"position\":5,\"id\":-5,\"type\":\"text\",\"conditions\":{\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Job\",\"position\":6,\"id\":-6,\"type\":\"text\",\"conditions\":{\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Contract Type\",\"type\":\"select\",\"id\":-7,\"position\":7,\"conditions\":{\"multiple\":false,\"data\":[{\"text\":\"Probation Contract\",\"value\":\"Probation Contract\"},{\"text\":\"Labor contract follow a certain job to have duration less than 12 months\",\"value\":\"Labor contract follow a certain job to have duration less than 12 months\"},{\"text\":\"Definite-term Labor contract\",\"value\":\"Definite-term Labor contract\"},{\"text\":\"Indefinite-term Labor contract\",\"value\":\"Indefinite-term Labor contract\"},{\"text\":\"Seasonal Contract\",\"value\":\"Seasonal Contract\"},{\"text\":\"Fresher Training Contract\",\"value\":\"Fresher Training Contract\"},{\"text\":\"Intern Training Contract\",\"value\":\"Intern Training Contract\"},{\"text\":\"Service Contract\",\"value\":\"Service Contract\"},{\"text\":\"Vender Contract\",\"value\":\"Vender Contract\"},{\"text\":\"Internship\",\"value\":\"Internship\"},{\"text\":\"Customer\",\"value\":\"Customer\"},{\"text\":\"Other\",\"value\":\"Other\"}],\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Start date\",\"type\":\"date\",\"position\":8,\"id\":-8,\"conditions\":{\"required\":true,\"format\":{\"text\":\"Please input dd/MM/yyyy\",\"regex\":\"dd/MM/yyyy\"}}},{\"name\":\"End date\",\"type\":\"date\",\"position\":9,\"id\":-9,\"conditions\":{\"required\":false,\"format\":{\"text\":\"Please input dd/MM/yyyy\",\"regex\":\"dd/MM/yyyy\"}}},{\"name\":\"Account\",\"type\":\"text\",\"position\":10,\"id\":-10,\"conditions\":{}},{\"name\":\"Add To Group\",\"type\":\"text\",\"position\":11,\"id\":-11,\"conditions\":{\"required\":true,\"min-length\":5,\"max-length\":1000}},{\"name\":\"Note\",\"type\":\"textarea\",\"position\":12,\"id\":-12,\"conditions\":{\"max-length\":1000}}],\"values\":[{\"columnId\":-3,\"row\":1,\"value\":\"cot 3 dong 1\"},{\"columnId\":-1,\"row\":2,\"value\":\"cot 1 dong 2\"},{\"columnId\":-2,\"row\":3,\"value\":\"cot 2 dong 3\"},{\"columnId\":-4,\"row\":3,\"value\":\"cot 4 dong 3\"},{\"columnId\":-5,\"row\":2,\"value\":\"cot 5 dong 2\"},{\"columnId\":-7,\"row\":1,\"value\":\"cot 7 dong 1\"},{\"columnId\":-7,\"row\":2,\"value\":\"cot 7 dong 2\"}]}},{\"dataType\":\"matrix\",\"name\":\"Matrix Information Of Ticket\",\"id\":-3,\"position\":3,\"data\":{\"columns\":[{\"name\":\"No.\",\"id\":-1,\"position\":1},{\"name\":\"Fullname\",\"position\":2,\"id\":-2},{\"name\":\"Gender\",\"position\":3,\"id\":-3}],\"rows\":[{\"name\":\"No.\",\"id\":-1,\"position\":1},{\"name\":\"Fullname\",\"position\":2,\"id\":-2},{\"name\":\"Gender\",\"position\":3,\"id\":-3}],\"values\":[{\"columnId\":-1,\"rowId\":-1,\"value\":\"dong 1 1\",\"conditions\":{\"required\":true,\"min-length\":5,\"max-length\":255},\"type\":\"text\"},{\"columnId\":-2,\"rowId\":-1,\"value\":\"dong 2 1\",\"conditions\":{\"required\":true,\"format\":{\"text\":\"Please input dd/MM/yyyy\",\"regex\":\"dd/MM/yyyy\"}},\"type\":\"date\"},{\"columnId\":-3,\"rowId\":-1,\"value\":\"dong 3 1\",\"conditions\":{\"multiple\":false,\"data\":[{\"text\":\"Male\",\"value\":\"Male\"},{\"text\":\"Female\",\"value\":\"Female\"}]},\"type\":\"select\"},{\"columnId\":-1,\"rowId\":-2,\"value\":\"dong 1 2\",\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255},\"type\":\"number\"},{\"columnId\":-2,\"rowId\":-2,\"value\":\"dong 2 2\",\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255},\"type\":\"number\"},{\"columnId\":-3,\"rowId\":-2,\"value\":\"dong 3 2\",\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255},\"type\":\"number\"},{\"columnId\":-1,\"rowId\":-3,\"value\":\"dong 1 3\",\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255},\"type\":\"number\"},{\"columnId\":-2,\"rowId\":-3,\"value\":\"dong 2 3\",\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255},\"type\":\"number\"}]}},{\"dataType\":\"matrix\",\"name\":\"Matrix Information Of Service\",\"id\":-6,\"position\":6,\"data\":{\"columns\":[{\"name\":\"No.\",\"id\":-1,\"position\":1},{\"name\":\"Fullname\",\"position\":2,\"id\":-2},{\"name\":\"Gender\",\"position\":3,\"id\":-3}],\"rows\":[{\"name\":\"No.\",\"id\":-1,\"position\":1},{\"name\":\"Fullname\",\"position\":2,\"id\":-2},{\"name\":\"Gender\",\"position\":3,\"id\":-3}],\"values\":[{\"columnId\":-1,\"rowId\":-1,\"value\":\"dong 1 1\",\"conditions\":{\"required\":true,\"min-length\":5,\"max-length\":255},\"type\":\"text\"},{\"columnId\":-2,\"rowId\":-1,\"value\":\"dong 2 1\",\"conditions\":{\"required\":true,\"format\":{\"text\":\"Please input dd/MM/yyyy\",\"regex\":\"dd/MM/yyyy\"}},\"type\":\"date\"},{\"columnId\":-3,\"rowId\":-1,\"value\":\"dong 3 1\",\"conditions\":{\"multiple\":false,\"data\":[{\"text\":\"Male\",\"value\":\"Male\"},{\"text\":\"Female\",\"value\":\"Female\"}]},\"type\":\"select\"},{\"columnId\":-1,\"rowId\":-2,\"value\":\"dong 1 2\",\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255},\"type\":\"number\"},{\"columnId\":-2,\"rowId\":-2,\"value\":\"dong 2 2\",\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255},\"type\":\"number\"},{\"columnId\":-3,\"rowId\":-2,\"value\":\"dong 3 2\",\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255},\"type\":\"number\"},{\"columnId\":-1,\"rowId\":-3,\"value\":\"dong 1 3\",\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255},\"type\":\"number\"},{\"columnId\":-2,\"rowId\":-3,\"value\":\"dong 2 3\",\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255},\"type\":\"number\"}]}}]}";

		// BuuPV: 20160121 [Convert String to Data Object]
		GsonBuilder gsonBilder = new GsonBuilder();
		gsonBilder.registerTypeAdapter(ElementData.class, new DataAdapter());

		// BuuPV: 20160129 Initialize data object for validating
		Gson gson2 = gsonBilder.create();
		DataObject vc = gson2.fromJson(json, DataObject.class);

		DataObject dataObject = new DataObject();
		List<ElementData> listElementData = dataObject.getElements();

		HSSFWorkbook workbook = new HSSFWorkbook(file);

		// BuuPV: 20160129 Get first sheet from the workbook
		HSSFSheet sheet = workbook.getSheetAt(0);
		sheet.setColumnHidden(0, false);
		// BuuPV: 20160129 Variable for position of each element
		int position = 1;
		// Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = sheet.iterator();

		// BuuPV: 20160129 Read every row
		while (rowIterator.hasNext()) {
			Row nextRow = rowIterator.next();

			for (int j = 0; j < vc.getElements().size(); j++) {
				if (nextRow != null) {
					if (nextRow.getCell(0, Row.RETURN_BLANK_AS_NULL) != null) {
						if (vc.getElements().get(j).getId() == nextRow.getCell(0).getNumericCellValue()) {
							// BuuPV: 20160125 Temporarily Check type based on
							// Name
							switch (vc.getElements().get(j).getDataType()) {
							case "individual":

								Individual individual = new Individual();
								individual.setDataType("individual");
								individual.setId((int) nextRow.getCell(0).getNumericCellValue());
								individual.setName(nextRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
								individual.setPosition(position++);
								IndividualData individualData = new IndividualData();
								System.out.println("Null ne: " + nextRow.getRowNum());
								individualData
										.setValue(nextRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
								individual.setData(individualData);
								listElementData.add(individual);

								// BuuPV: 20160129Validate Individual Data
								ExcelValidatorModel validateCell = new ExcelValidatorModel();
								validateCell.setElementId((int) nextRow.getCell(0).getNumericCellValue());
								validateCell.setRow(nextRow.getRowNum() + 1);
								validateCell.setColumn(CellReference.convertNumToColString(
										nextRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getColumnIndex()));
								validateCell.setType("individual");
								validateCell.setValue(individualData.getValue());
								listValidateCell.add(validateCell);

								break;
							case "table":
								Table table = new Table();
								table.setDataType("table");
								table.setId((int) nextRow.getCell(0).getNumericCellValue());
								int elementId = (int) nextRow.getCell(0).getNumericCellValue();
								table.setName(nextRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
								table.setPosition(position++);
								TableData tableData = new TableData();
								List<TableColumn> listTableColumn = tableData.getColumns();
								List<TableValue> listTableValue = tableData.getValues();

								nextRow = rowIterator.next();
								int lastColumn = Math.max(nextRow.getLastCellNum(), 3);
								// BuuPV: 20160129 Variable for column header
								// position
								int headerOrder = 1;

								// BuuPV: 20160125 Read Table Column Header
								for (int cn = 0; cn < lastColumn; cn++) {
									Cell c = nextRow.getCell(cn, Row.RETURN_BLANK_AS_NULL);
									if (c == null) {
										// The spreadsheet is empty in this cell
									} else {
										TableColumn tableColumn = new TableColumn();
										tableColumn.setId((c.getColumnIndex() - 1) * -1);
										tableColumn.setName(c.getStringCellValue());

										tableColumn.setPosition(headerOrder++);
										// tableColumn.setType(type);
										listTableColumn.add(tableColumn);
										TableValidatorModel validateCell2 = new TableValidatorModel();
										System.out.println(CellReference.convertNumToColString(c.getColumnIndex()));
										validateCell2.setElementId(elementId);
										validateCell2.setId((c.getColumnIndex() - 1) * -1);
										validateCell2.setRow(c.getRowIndex() + 1);
										validateCell2
												.setColumn(CellReference.convertNumToColString(c.getColumnIndex()));
										validateCell2.setType("tableHeader");
										validateCell2.setValue(c.getStringCellValue());
										listValidateCell.add(validateCell2);

									}
								}

								nextRow = rowIterator.next();
								int rowStart = nextRow.getRowNum();
								int rowEnd = sheet.getLastRowNum();
								// BuuPV: 20160129 Variable to set data row
								int rowOrder = 0;

								// BuuPV: 20160129 Variable to check where row
								// is
								// enter
								int currentRow = 0;

								// BuuPV: 20160125 Read Table Data Value
								for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
									Row r = sheet.getRow(rowNum);
									if (r == null) {
										break;
									}
									// BuuPV: 20160129 Enter a row
									if (currentRow < rowNum) {
										currentRow = rowNum;
										rowOrder++;
									}
									for (int cn = 0; cn < lastColumn; cn++) {
										Cell c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
										if (c == null) {
											// The spreadsheet is empty in this
											// cell
										} else {

											TableValue tableValue = new TableValue();
											tableValue.setColumnId((c.getColumnIndex() - 1) * -1);
											tableValue.setRow(rowOrder);
											tableValue.setValue(c.getStringCellValue());
											listTableValue.add(tableValue);

											// Validate Table Data
											TableValidatorModel validateCell2 = new TableValidatorModel();
											System.out.println(CellReference.convertNumToColString(c.getColumnIndex()));
											validateCell2.setElementId(elementId);
											validateCell2.setId((c.getColumnIndex() - 1) * -1);
											validateCell2.setRow(c.getRowIndex() + 1);
											validateCell2
													.setColumn(CellReference.convertNumToColString(c.getColumnIndex()));
											validateCell2.setType("table");
											validateCell2.setValue(c.getStringCellValue());
											listValidateCell.add(validateCell2);
										}
									}
								}

								table.setData(tableData);
								listElementData.add(table);
								break;
							case "matrix":
								Matrix matrix = new Matrix();
								matrix.setDataType("matrix");
								matrix.setId((int) nextRow.getCell(0).getNumericCellValue());
								int elementId2 = (int) nextRow.getCell(0).getNumericCellValue();
								matrix.setName(nextRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
								matrix.setPosition(position++);
								MatrixData matrixData = new MatrixData();
								List<MatrixColumnRow> listMatrixColumn = matrixData.getColumns();
								List<MatrixColumnRow> listMatrixRow = matrixData.getRows();
								List<MatrixValue> listMatrixValue = matrixData.getValues();

								nextRow = rowIterator.next();
								lastColumn = nextRow.getLastCellNum();
								// BuuPV: 20160129 for header Position
								headerOrder = 1;

								// BuuPV: 20160125 Read Matrix Column Header
								for (int cn = 0; cn < lastColumn; cn++) {
									Cell c = nextRow.getCell(cn, Row.RETURN_BLANK_AS_NULL);
									if (c == null) {
										// The spreadsheet is empty in this cell
									} else {
										MatrixColumnRow matrixColumn = new MatrixColumnRow();
										matrixColumn.setId((c.getColumnIndex() - 2) * -1);
										matrixColumn.setName(c.getStringCellValue());
										matrixColumn.setPosition(headerOrder++);
										listMatrixColumn.add(matrixColumn);

										MatrixValidatorModel validateCell3 = new MatrixValidatorModel();
										validateCell3.setElementId(elementId2);
										validateCell3.setColumnId((c.getColumnIndex() - 2) * -1);
										validateCell3.setRow(c.getRowIndex() + 1);
										validateCell3
												.setColumn(CellReference.convertNumToColString(c.getColumnIndex()));
										validateCell3.setType("matrixHeader");
										validateCell3.setValue(c.getStringCellValue());
										listValidateCell.add(validateCell3);
									}
								}

								nextRow = rowIterator.next();

								int startRow = nextRow.getRowNum();
								int endRow = sheet.getLastRowNum();
								int matrixRowOrder = 0;
								int matrixRowPos = 0;

								// BuuPV: 20160125 Read Matrix Row header
								for (int rowNum = startRow; rowNum < endRow + 1; rowNum++) {
									Row r = sheet.getRow(rowNum);
									if (r == null) {
										break;
									}

									Cell c = r.getCell(nextRow.getFirstCellNum());
									if (c == null) {
										// The spreadsheet is empty in this cell
									} else {
										MatrixColumnRow matrixRow = new MatrixColumnRow();
										matrixRow.setId(--matrixRowOrder);
										matrixRow.setName(c.getStringCellValue());
										matrixRow.setPosition(++matrixRowPos);
										listMatrixRow.add(matrixRow);

										MatrixValidatorModel validateCell3 = new MatrixValidatorModel();
										validateCell3.setElementId(elementId2);
										validateCell3.setRowId((c.getRowIndex() - 11) * -1);
										validateCell3.setRow(c.getRowIndex() + 1);
										validateCell3
												.setColumn(CellReference.convertNumToColString(c.getColumnIndex()));
										validateCell3.setType("matrixRowHeader");
										validateCell3.setValue(c.getStringCellValue());
										listValidateCell.add(validateCell3);
									}
								}
								rowOrder = 0;
								currentRow = 0;

								// BuuPV: 20160125 Read matrix row
								for (int rowNum = startRow; rowNum < endRow; rowNum++) {
									Row r = sheet.getRow(rowNum);
									if (r == null) {
										break;
									}
									if (currentRow < rowNum) {
										currentRow = rowNum;
										rowOrder--;
									}

									// BuuPV: 20160129 read matrix data
									for (int cn = nextRow.getFirstCellNum() + 1; cn < lastColumn; cn++) {
										Cell c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
										if (c == null) {
											// The spreadsheet is empty in this
											// cell
										} else {
											MatrixValue matrixValue = new MatrixValue();
											matrixValue.setColumnId((c.getColumnIndex() - 2) * -1);
											matrixValue.setValue(c.getStringCellValue());
											matrixValue.setRowId(rowOrder);
											listMatrixValue.add(matrixValue);

											// BuuPV: 20160129 Validate Matrix
											// Data
											MatrixValidatorModel validateCell3 = new MatrixValidatorModel();
											validateCell3.setElementId(elementId2);
											validateCell3.setRowId(((c.getRowIndex() - 11) * -1));
											validateCell3.setColumnId((c.getColumnIndex() - 2) * -1);
											validateCell3.setRow(c.getRowIndex() + 1);
											validateCell3
													.setColumn(CellReference.convertNumToColString(c.getColumnIndex()));
											validateCell3.setType("matrix");
											validateCell3.setValue(c.getStringCellValue());
											listValidateCell.add(validateCell3);
										}
									}
								}

								matrix.setData(matrixData);
								listElementData.add(matrix);
								break;
							}
						} else {
							System.out.println("vkl" + nextRow.getRowNum() + 1);
						}
					}
				}

			}
			// BuuPV: 20160129 Read every cell

		}
		dataObject.setElements(listElementData);

		workbook.close();
		Gson gson = new Gson();
		System.out.println(gson.toJson(dataObject));

		// Check size
		ExcelValidator.checkImportExcelFormat(errorList, vc, dataObject);

		// BuuPV: 20160129 Test function
		ExcelValidator.checkImportExcelData(errorList, vc, listValidateCell);
		for (ValidatorErrorModelException error : errorList) {
			System.out.println(error.getErrorMessage());
		}

		if (errorList.size() != 0) {
		}
		// Get iterator to all cells of current row
		// Iterator<Cell> cellIterator = row.cellIterator();
		return "upload";
	}

	@RequestMapping("/export")
	public String export(HttpServletResponse response) throws IOException {
		int hide = 0;
		// BuuPV: 20160121 [JSON String for test]
		String json = "{\"elements\":[{\"dataType\":\"individual\",\"name\":\"Link to Employees Picture Folder\",\"id\":-1,\"position\":1,\"data\":{\"value\":\"thu\",\"type\":\"select\",\"conditions\":{\"multiple\":false,\"data\":[{\"text\":\"Male\",\"value\":\"Male\"},{\"text\":\"Female\",\"value\":\"Female\"}]}}},{\"dataType\":\"individual\",\"name\":\"Link to Employees Picture Folder\",\"id\":-4,\"position\":4,\"data\":{\"value\":\"coi\",\"type\":\"select\",\"conditions\":{\"multiple\":false,\"data\":[{\"text\":\"Male\",\"value\":\"Male\"},{\"text\":\"Female\",\"value\":\"Female\"}]}}},{\"dataType\":\"table\",\"name\":\"Table Information Of Service\",\"id\":-5,\"position\":5,\"data\":{\"columns\":[{\"name\":\"No.\",\"type\":\"number\",\"id\":-1,\"position\":1,\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Fullname\",\"type\":\"text\",\"position\":2,\"id\":-2,\"conditions\":{\"required\":true,\"min-length\":5,\"max-length\":255}},{\"name\":\"Gender\",\"type\":\"select\",\"conditions\":{\"multiple\":false,\"data\":[{\"text\":\"Male\",\"value\":\"Male\"},{\"text\":\"Female\",\"value\":\"Female\"}]},\"position\":3,\"id\":-3},{\"name\":\"Parent Department\",\"position\":4,\"id\":-4,\"type\":\"text\",\"conditions\":{\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Child Department\",\"position\":5,\"id\":-5,\"type\":\"text\",\"conditions\":{\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Job\",\"position\":6,\"id\":-6,\"type\":\"text\",\"conditions\":{\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Contract Type\",\"type\":\"select\",\"id\":-7,\"position\":7,\"conditions\":{\"multiple\":false,\"data\":[{\"text\":\"Probation Contract\",\"value\":\"Probation Contract\"},{\"text\":\"Labor contract follow a certain job to have duration less than 12 months\",\"value\":\"Labor contract follow a certain job to have duration less than 12 months\"},{\"text\":\"Definite-term Labor contract\",\"value\":\"Definite-term Labor contract\"},{\"text\":\"Indefinite-term Labor contract\",\"value\":\"Indefinite-term Labor contract\"},{\"text\":\"Seasonal Contract\",\"value\":\"Seasonal Contract\"},{\"text\":\"Fresher Training Contract\",\"value\":\"Fresher Training Contract\"},{\"text\":\"Intern Training Contract\",\"value\":\"Intern Training Contract\"},{\"text\":\"Service Contract\",\"value\":\"Service Contract\"},{\"text\":\"Vender Contract\",\"value\":\"Vender Contract\"},{\"text\":\"Internship\",\"value\":\"Internship\"},{\"text\":\"Customer\",\"value\":\"Customer\"},{\"text\":\"Other\",\"value\":\"Other\"}],\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Start date\",\"type\":\"date\",\"position\":8,\"id\":-8,\"conditions\":{\"required\":true,\"format\":{\"text\":\"Please input dd/MM/yyyy\",\"regex\":\"dd/MM/yyyy\"}}},{\"name\":\"End date\",\"type\":\"date\",\"position\":9,\"id\":-9,\"conditions\":{\"required\":false,\"format\":{\"text\":\"Please input dd/MM/yyyy\",\"regex\":\"dd/MM/yyyy\"}}},{\"name\":\"Account\",\"type\":\"text\",\"position\":10,\"id\":-10,\"conditions\":{}},{\"name\":\"Add To Group\",\"type\":\"text\",\"position\":11,\"id\":-11,\"conditions\":{\"required\":true,\"min-length\":5,\"max-length\":1000}},{\"name\":\"Note\",\"type\":\"textarea\",\"position\":12,\"id\":-12,\"conditions\":{\"max-length\":1000}}],\"values\":[{\"columnId\":-3,\"row\":1,\"value\":\"cot 3 dong 1\"},{\"columnId\":-1,\"row\":2,\"value\":\"cot 1 dong 2\"},{\"columnId\":-2,\"row\":3,\"value\":\"cot 2 dong 3\"},{\"columnId\":-4,\"row\":3,\"value\":\"cot 4 dong 3\"},{\"columnId\":-5,\"row\":2,\"value\":\"cot 5 dong 2\"},{\"columnId\":-7,\"row\":1,\"value\":\"cot 7 dong 1\"},{\"columnId\":-7,\"row\":2,\"value\":\"cot 7 dong 2\"}]}},{\"dataType\":\"table\",\"name\":\"Table Information Of Ticket\",\"id\":-2,\"position\":2,\"data\":{\"columns\":[{\"name\":\"No.\",\"type\":\"number\",\"id\":-1,\"position\":1,\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Fullname\",\"type\":\"text\",\"position\":2,\"id\":-2,\"conditions\":{\"required\":true,\"min-length\":5,\"max-length\":255}},{\"name\":\"Gender\",\"type\":\"select\",\"conditions\":{\"multiple\":false,\"data\":[{\"text\":\"Male\",\"value\":\"Male\"},{\"text\":\"Female\",\"value\":\"Female\"}]},\"position\":3,\"id\":-3},{\"name\":\"Parent Department\",\"position\":4,\"id\":-4,\"type\":\"text\",\"conditions\":{\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Child Department\",\"position\":5,\"id\":-5,\"type\":\"text\",\"conditions\":{\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Job\",\"position\":6,\"id\":-6,\"type\":\"text\",\"conditions\":{\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Contract Type\",\"type\":\"select\",\"id\":-7,\"position\":7,\"conditions\":{\"multiple\":false,\"data\":[{\"text\":\"Probation Contract\",\"value\":\"Probation Contract\"},{\"text\":\"Labor contract follow a certain job to have duration less than 12 months\",\"value\":\"Labor contract follow a certain job to have duration less than 12 months\"},{\"text\":\"Definite-term Labor contract\",\"value\":\"Definite-term Labor contract\"},{\"text\":\"Indefinite-term Labor contract\",\"value\":\"Indefinite-term Labor contract\"},{\"text\":\"Seasonal Contract\",\"value\":\"Seasonal Contract\"},{\"text\":\"Fresher Training Contract\",\"value\":\"Fresher Training Contract\"},{\"text\":\"Intern Training Contract\",\"value\":\"Intern Training Contract\"},{\"text\":\"Service Contract\",\"value\":\"Service Contract\"},{\"text\":\"Vender Contract\",\"value\":\"Vender Contract\"},{\"text\":\"Internship\",\"value\":\"Internship\"},{\"text\":\"Customer\",\"value\":\"Customer\"},{\"text\":\"Other\",\"value\":\"Other\"}],\"required\":true,\"min-length\":1,\"max-length\":255}},{\"name\":\"Start date\",\"type\":\"date\",\"position\":8,\"id\":-8,\"conditions\":{\"required\":true,\"format\":{\"text\":\"Please input dd/MM/yyyy\",\"regex\":\"dd/MM/yyyy\"}}},{\"name\":\"End date\",\"type\":\"date\",\"position\":9,\"id\":-9,\"conditions\":{\"required\":false,\"format\":{\"text\":\"Please input dd/MM/yyyy\",\"regex\":\"dd/MM/yyyy\"}}},{\"name\":\"Account\",\"type\":\"text\",\"position\":10,\"id\":-10,\"conditions\":{}},{\"name\":\"Add To Group\",\"type\":\"text\",\"position\":11,\"id\":-11,\"conditions\":{\"required\":true,\"min-length\":5,\"max-length\":1000}},{\"name\":\"Note\",\"type\":\"textarea\",\"position\":12,\"id\":-12,\"conditions\":{\"max-length\":1000}}],\"values\":[{\"columnId\":-3,\"row\":1,\"value\":\"cot 3 dong 1\"},{\"columnId\":-1,\"row\":2,\"value\":\"cot 1 dong 2\"},{\"columnId\":-2,\"row\":3,\"value\":\"cot 2 dong 3\"},{\"columnId\":-4,\"row\":3,\"value\":\"cot 4 dong 3\"},{\"columnId\":-5,\"row\":2,\"value\":\"cot 5 dong 2\"},{\"columnId\":-7,\"row\":1,\"value\":\"cot 7 dong 1\"},{\"columnId\":-7,\"row\":2,\"value\":\"cot 7 dong 2\"}]}},{\"dataType\":\"matrix\",\"name\":\"Matrix Information Of Ticket\",\"id\":-3,\"position\":3,\"data\":{\"columns\":[{\"name\":\"No.\",\"id\":-1,\"position\":1},{\"name\":\"Fullname\",\"position\":2,\"id\":-2},{\"name\":\"Gender\",\"position\":3,\"id\":-3}],\"rows\":[{\"name\":\"No.\",\"id\":-1,\"position\":1},{\"name\":\"Fullname\",\"position\":2,\"id\":-2},{\"name\":\"Gender\",\"position\":3,\"id\":-3}],\"values\":[{\"columnId\":-1,\"rowId\":-1,\"value\":\"dong 1 1\",\"conditions\":{\"required\":true,\"min-length\":5,\"max-length\":255},\"type\":\"text\"},{\"columnId\":-2,\"rowId\":-1,\"value\":\"dong 2 1\",\"conditions\":{\"required\":true,\"format\":{\"text\":\"Please input dd/MM/yyyy\",\"regex\":\"dd/MM/yyyy\"}},\"type\":\"date\"},{\"columnId\":-3,\"rowId\":-1,\"value\":\"dong 3 1\",\"conditions\":{\"multiple\":false,\"data\":[{\"text\":\"Male\",\"value\":\"Male\"},{\"text\":\"Female\",\"value\":\"Female\"}]},\"type\":\"select\"},{\"columnId\":-1,\"rowId\":-2,\"value\":\"dong 1 2\",\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255},\"type\":\"number\"},{\"columnId\":-2,\"rowId\":-2,\"value\":\"dong 2 2\",\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255},\"type\":\"number\"},{\"columnId\":-3,\"rowId\":-2,\"value\":\"dong 3 2\",\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255},\"type\":\"number\"},{\"columnId\":-1,\"rowId\":-3,\"value\":\"dong 1 3\",\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255},\"type\":\"number\"},{\"columnId\":-2,\"rowId\":-3,\"value\":\"dong 2 3\",\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255},\"type\":\"number\"}]}},{\"dataType\":\"matrix\",\"name\":\"Matrix Information Of Service\",\"id\":-6,\"position\":6,\"data\":{\"columns\":[{\"name\":\"No.\",\"id\":-1,\"position\":1},{\"name\":\"Fullname\",\"position\":2,\"id\":-2},{\"name\":\"Gender\",\"position\":3,\"id\":-3}],\"rows\":[{\"name\":\"No.\",\"id\":-1,\"position\":1},{\"name\":\"Fullname\",\"position\":2,\"id\":-2},{\"name\":\"Gender\",\"position\":3,\"id\":-3}],\"values\":[{\"columnId\":-1,\"rowId\":-1,\"value\":\"dong 1 1\",\"conditions\":{\"required\":true,\"min-length\":5,\"max-length\":255},\"type\":\"text\"},{\"columnId\":-2,\"rowId\":-1,\"value\":\"dong 2 1\",\"conditions\":{\"required\":true,\"format\":{\"text\":\"Please input dd/MM/yyyy\",\"regex\":\"dd/MM/yyyy\"}},\"type\":\"date\"},{\"columnId\":-3,\"rowId\":-1,\"value\":\"dong 3 1\",\"conditions\":{\"multiple\":false,\"data\":[{\"text\":\"Male\",\"value\":\"Male\"},{\"text\":\"Female\",\"value\":\"Female\"}]},\"type\":\"select\"},{\"columnId\":-1,\"rowId\":-2,\"value\":\"dong 1 2\",\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255},\"type\":\"number\"},{\"columnId\":-2,\"rowId\":-2,\"value\":\"dong 2 2\",\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255},\"type\":\"number\"},{\"columnId\":-3,\"rowId\":-2,\"value\":\"dong 3 2\",\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255},\"type\":\"number\"},{\"columnId\":-1,\"rowId\":-3,\"value\":\"dong 1 3\",\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255},\"type\":\"number\"},{\"columnId\":-2,\"rowId\":-3,\"value\":\"dong 2 3\",\"conditions\":{\"format\":{\"text\":\"Please input an number!\",\"regex\":\"([0-9]*)\"},\"required\":true,\"min-length\":1,\"max-length\":255},\"type\":\"number\"}]}}]}";

		// BuuPV: 20160121 [Convert String to Data Object[
		GsonBuilder gsonBilder = new GsonBuilder();
		gsonBilder.registerTypeAdapter(ElementData.class, new DataAdapter());

		Gson gson = gsonBilder.create();
		DataObject vc = gson.fromJson(json, DataObject.class);

		// BuuPV: 20160121 [Initialize new WorkBook]
		HSSFWorkbook workbook = new HSSFWorkbook();

		// BuuPV: 20160121 [Initialize new WorkSheet]
		HSSFSheet worksheet = workbook.createSheet("Data");
		HSSFSheet hidden;
		// BuuPV: 20160121 [Initialize row and cell for writing]
		HSSFRow row;
		HSSFCell cell;

		// BuuPV: 20160121 [Sort on position. Higher is placing later]
		Collections.sort(vc.getElements());

		// BuuPV: 20160121 [Writing data to Excel]
		for (ElementData v : vc.getElements()) {

			// BuuPV: 20160121 [Get the last row]
			int lastRow = worksheet.getLastRowNum();

			// BuuPV: 20160121 [Writing template based on dataType]
			switch (v.getClass().getName().replaceAll("vn.com.splussoftware.sms.utils.service.jsonhandler.", "")) {
			case "Individual":
				// BuuPV: 20160122 Padding
				row = worksheet.createRow(lastRow += 2);

				// BuuPV: 20160122 Casting
				Individual individual = (Individual) v;

				// BuuPV: 20160122 Write ID
				cell = row.createCell(0);
				cell.setCellValue(v.getId());

				// BuuPV: 20160122 Write Name
				cell = row.createCell(1);
				cell.setCellValue(v.getName());
				cell = row.createCell(2);
				HSSFCellStyle cellStyle = workbook.createCellStyle();
				switch (individual.getData().getType()) {
				case "text":
				case "textarea":
					cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
					break;

				case "number":
					cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
					break;
				case "date":
					cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("d-mmm-yy"));
					break;
				case "select":
					List<String> selection = individual.getData().getConditions().getSelectData();
					String[] array = new String[selection.size()];
					for (int i = 0; i < selection.size(); i++)
						array[i] = selection.get(i);

					String sheetName = individual.getData().getValue().replaceAll(" ", "_");
					if (workbook.getSheet(sheetName) == null) {
						hidden = workbook.createSheet(sheetName);
						hide++;
					} else {
						hidden = workbook.getSheet(sheetName);
					}
					for (int i = 0, length = array.length; i < length; i++) {
						String name = array[i];
						HSSFRow rowSelect = hidden.createRow(i);
						HSSFCell cellSelect = rowSelect.createCell(0);
						cellSelect.setCellValue(name);
					}
					CellRangeAddressList addressList = new CellRangeAddressList(cell.getRowIndex(), cell.getRowIndex(),
							cell.getColumnIndex(), cell.getColumnIndex());
					DVConstraint dvConstraint = DVConstraint
							.createFormulaListConstraint(sheetName + "!$A$1:$A$" + array.length);
					DataValidation dataValidation = new HSSFDataValidation(addressList, dvConstraint);
					dataValidation.setSuppressDropDownArrow(false);
					worksheet.addValidationData(dataValidation);
					break;

				}
				// BuuPV: 20160122 Write Data
				cell.setCellStyle(cellStyle);
				cell.setCellValue(individual.getData().getValue());

				break;

			case "Table":
				row = worksheet.createRow(lastRow += 2);

				// BuuPV: 20160124 Casting
				Table table = (Table) v;
				List<TableColumn> listTableColumn = table.getData().getColumns();
				List<TableValue> listTableValue = table.getData().getValues();

				// BuuPV: 20160124 Write ID
				cell = row.createCell(0);
				cell.setCellValue(v.getId());

				// BuuPV: 20160124 Write Name
				cell = row.createCell(1);
				cell.setCellValue(v.getName());
				cell = row.createCell(2);
				// BuuPV: 20160124 Variable for write header
				int a = 1;
				row = worksheet.createRow(worksheet.getLastRowNum() + 1);

				// BuuPV: 20160124 Initialize 4-dimension Array
				// storing row,column and value
				List<HashMap<Integer, HashMap<Integer, String>>> rowMap = new ArrayList<HashMap<Integer, HashMap<Integer, String>>>();

				// BuuPV: 20160124 Sort column based on position
				Collections.sort(listTableColumn);
				for (TableColumn tableColumn : listTableColumn) {

					cell = row.createCell(++a);
					cell.setCellValue(tableColumn.getName());
				}

				List<TableValue> values = listTableValue;

				// BuuPV: 20160124 Sort value based on Row
				Collections.sort(values);

				// BuuPV: 20160124 Variable to get current row
				int currentRow = 0;

				// BuuPV: 20160124 Set data to map
				for (int i = 0; i < values.size(); i++) {

					int currentColumnOrder = 0;

					// BuuPV: 20160124 Check whether row is increased
					if (values.get(i).getRow() > currentRow) {
						currentRow = values.get(i).getRow();
					}

					HashMap<Integer, String> tmpCell = new HashMap<>();

					//
					for (TableColumn tableColumn : listTableColumn) {
						currentColumnOrder++;

						// BuuPV: 20160124 put value with corresponding column
						// to ColumnMap
						if (tableColumn.getId() == values.get(i).getColumnId()) {
							tmpCell.put(currentColumnOrder, values.get(i).getValue());
							break;
						}
					}

					// BuuPV: 20160124 Put Column,Value and Row to Map
					HashMap<Integer, HashMap<Integer, String>> tmpRowData = new HashMap<Integer, HashMap<Integer, String>>();
					tmpRowData.put(currentRow, tmpCell);

					// BuuPV: 20160124 Add the map to a list
					rowMap.add(tmpRowData);
				}

				row = worksheet.createRow(worksheet.getLastRowNum() + 1);

				int b = 1;

				// BuuPV: 20160124 Variable for row enter purpose
				currentRow = 1;
				for (HashMap<Integer, HashMap<Integer, String>> tmpRowData : rowMap) {
					for (Integer key : tmpRowData.keySet()) {
						HashMap<Integer, String> rowData = tmpRowData.get(key);
						if (currentRow < key) {
							row = worksheet.createRow(worksheet.getLastRowNum() + 1);
							currentRow = key;
						}
						for (Integer key2 : rowData.keySet()) {
							System.out.println(key + " - " + key2);
							cell = row.createCell(b + key2);
							HSSFCellStyle cellStyle2 = workbook.createCellStyle();

							// BuuPV: 20160124 Create Cell Style
							switch (listTableColumn.get(key2 - 1).getType()) {
							case "date":
								cellStyle2.setDataFormat(HSSFDataFormat.getBuiltinFormat("d-mmm-yy"));
								break;
							case "text":
							case "textarea":
								cellStyle2.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
								break;
							case "number":
								cellStyle2.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
								break;
							case "select":

								List<String> selection = listTableColumn.get(key2 - 1).getConditions().getSelectData();
								String[] array = new String[selection.size()];
								for (int i = 0; i < selection.size(); i++)
									array[i] = selection.get(i);

								String sheetName = listTableColumn.get(key2 - 1).getName().replaceAll(" ", "_");
								if (workbook.getSheet(sheetName) == null) {
									hidden = workbook.createSheet(sheetName);
									hide++;
								} else {
									hidden = workbook.getSheet(sheetName);
								}
								for (int i = 0, length = array.length; i < length; i++) {
									String name = array[i];
									HSSFRow rowSelect = hidden.createRow(i);
									HSSFCell cellSelect = rowSelect.createCell(0);
									cellSelect.setCellValue(name);
								}
								CellRangeAddressList addressList = new CellRangeAddressList(cell.getRowIndex(),
										cell.getRowIndex(), cell.getColumnIndex(), cell.getColumnIndex());
								DVConstraint dvConstraint = DVConstraint
										.createFormulaListConstraint(sheetName + "!$A$1:$A$" + array.length);
								DataValidation dataValidation = new HSSFDataValidation(addressList, dvConstraint);
								dataValidation.setSuppressDropDownArrow(false);
								worksheet.addValidationData(dataValidation);
								break;
							}
							cell.setCellStyle(cellStyle2);
							cell.setCellValue(rowData.get(key2));

						}

					}

				}
				break;
			case "Matrix":
				row = worksheet.createRow(lastRow += 2);

				// BuuPV: 20160124 Casting Matrix
				Matrix matrix = (Matrix) v;
				List<MatrixColumnRow> listMatrixColumn = matrix.getData().getColumns();
				List<MatrixColumnRow> listMatrixRow = matrix.getData().getRows();
				List<MatrixValue> listMatrixValue = matrix.getData().getValues();

				// BuuPV: 20160124 Write ID
				cell = row.createCell(0);
				cell.setCellValue(v.getId());

				// BuuPV: 20160124 Write Name
				cell = row.createCell(1);
				cell.setCellValue(v.getName());
				cell = row.createCell(2);
				int d = 2;
				row = worksheet.createRow(worksheet.getLastRowNum() + 1);
				List<HashMap<Integer, HashMap<Integer, String>>> matrixRowMap = new ArrayList<HashMap<Integer, HashMap<Integer, String>>>();

				// Sort based on position
				Collections.sort(listMatrixColumn);
				for (MatrixColumnRow matrixColumn : listMatrixColumn) {

					cell = row.createCell(++d);
					cell.setCellValue(matrixColumn.getName());
				}

				List<MatrixValue> matrixValues = listMatrixValue;
				Collections.sort(matrixValues);

				// BuuPV: 20160124 Set data to map

				int matrixCurrentRow = 0;
				for (int i = 0; i < matrixValues.size(); i++) {
					int currentColumnOrder = 0;
					if (matrixValues.get(i).getRowId() * -1 > matrixCurrentRow) {
						matrixCurrentRow = matrixValues.get(i).getRowId() * -1;
					}
					HashMap<Integer, String> tmpCell = new HashMap<>();

					for (MatrixColumnRow matrixColumn : listMatrixColumn) {
						currentColumnOrder++;
						if (matrixColumn.getId() == matrixValues.get(i).getColumnId()) {
							tmpCell.put(currentColumnOrder, matrixValues.get(i).getValue());
							break;
						}
					}
					HashMap<Integer, HashMap<Integer, String>> tmpRowData = new HashMap<Integer, HashMap<Integer, String>>();
					tmpRowData.put(matrixCurrentRow, tmpCell);
					matrixRowMap.add(tmpRowData);
				}

				row = worksheet.createRow(worksheet.getLastRowNum() + 1);
				int e = 2;
				matrixCurrentRow = 1;
				int count = 0;
				for (HashMap<Integer, HashMap<Integer, String>> tmpRowData : matrixRowMap) {
					for (Integer key : tmpRowData.keySet()) {
						HashMap<Integer, String> rowData = tmpRowData.get(key);
						if (matrixCurrentRow == key) {

							for (MatrixColumnRow matrixRow : listMatrixRow) {
								if (matrixRow.getId() * -1 == key) {
									cell = row.createCell(2);
									cell.setCellValue(matrixRow.getName());
								}
							}
						}
						if (matrixCurrentRow < key) {

							row = worksheet.createRow(worksheet.getLastRowNum() + 1);
							matrixCurrentRow = key;
						}

						for (Integer key2 : rowData.keySet()) {
							if (count == 0) {
								count = key2 - 1;
							} else
								count++;
							System.out.println(key + " - " + key2);
							cell = row.createCell(e + key2);
							HSSFCellStyle cellStyle2 = workbook.createCellStyle();

							// BuuPV: 20160124 Create Cell Style
							System.out.println(listMatrixValue.get(count).getColumnId() + " - "
									+ listMatrixValue.get(count).getRowId() + " - "
									+ listMatrixValue.get(count).getType());
							switch (listMatrixValue.get(count).getType()) {
							case "date":
								cellStyle2.setDataFormat(HSSFDataFormat.getBuiltinFormat("d-mmm-yy"));
								break;
							case "text":
							case "textarea":
								cellStyle2.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
								break;
							case "number":
								cellStyle2.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
								break;
							case "select":
								List<String> selection = listMatrixValue.get(count).getConditions().getSelectData();
								System.out.println(listMatrixValue.get(count).getColumnId() + " - "
										+ listMatrixValue.get(count).getRowId() + selection.size());
								String[] array = new String[selection.size()];
								for (int i = 0; i < selection.size(); i++)
									array[i] = selection.get(i);

								String sheetName = listMatrixValue.get(count).getValue().replaceAll(" ", "_");
								if (workbook.getSheet(sheetName) == null) {
									hidden = workbook.createSheet(sheetName);
									hide++;
								} else {
									hidden = workbook.getSheet(sheetName);
								}
								for (int i = 0, length = array.length; i < length; i++) {
									String name = array[i];
									HSSFRow rowSelect = hidden.createRow(i);
									HSSFCell cellSelect = rowSelect.createCell(0);
									cellSelect.setCellValue(name);
								}
								CellRangeAddressList addressList = new CellRangeAddressList(cell.getRowIndex(),
										cell.getRowIndex(), cell.getColumnIndex(), cell.getColumnIndex());
								DVConstraint dvConstraint = DVConstraint
										.createFormulaListConstraint(sheetName + "!$A$1:$A$" + array.length);
								DataValidation dataValidation = new HSSFDataValidation(addressList, dvConstraint);
								dataValidation.setSuppressDropDownArrow(false);
								worksheet.addValidationData(dataValidation);
								break;
							}
							cell.setCellStyle(cellStyle2);
							cell.setCellValue(rowData.get(key2));

						}
					}

				}
				break;
			default:
				break;

			}

		}

		// BuuPV: 20160121 [Set output file type]
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=\"Json.xls\"");
		try {
			OutputStream outputStream = response.getOutputStream();
			worksheet.setColumnHidden(0, true);
			for (int i = 1; i <= hide; i++) {
				workbook.setSheetHidden(i, true);
			}
			workbook.write(outputStream);
			workbook.close();
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Done";
	}

}
