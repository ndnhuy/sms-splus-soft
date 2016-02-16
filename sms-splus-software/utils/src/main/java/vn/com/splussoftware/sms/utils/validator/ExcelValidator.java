package vn.com.splussoftware.sms.utils.validator;

import java.util.ArrayList;
import java.util.List;

import vn.com.splussoftware.sms.model.exception.ExcelValidatorModel;
import vn.com.splussoftware.sms.model.exception.MatrixValidatorModel;
import vn.com.splussoftware.sms.model.exception.TableValidatorModel;
import vn.com.splussoftware.sms.model.exception.ValidatorErrorModelException;
import vn.com.splussoftware.sms.utils.constant.ExcelValidatorConstant;
import vn.com.splussoftware.sms.utils.constant.UtilValidatorConstant;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Condition;
import vn.com.splussoftware.sms.utils.service.jsonhandler.DataObject;
import vn.com.splussoftware.sms.utils.service.jsonhandler.ElementData;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Individual;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Matrix;
import vn.com.splussoftware.sms.utils.service.jsonhandler.MatrixColumnRow;
import vn.com.splussoftware.sms.utils.service.jsonhandler.MatrixValue;
import vn.com.splussoftware.sms.utils.service.jsonhandler.Table;
import vn.com.splussoftware.sms.utils.service.jsonhandler.TableColumn;

public class ExcelValidator {
	public static List<ValidatorErrorModelException> checkImportExcelData(List<ValidatorErrorModelException> errorList,
			DataObject baseData, List<ExcelValidatorModel> dataNeedChecking) {
		ValidatorErrorModelException check = new ValidatorErrorModelException();
		Condition condition = new Condition();
		List<TableColumn> baseTableColumn = new ArrayList<>();
		List<MatrixValue> baseMatrixValue = new ArrayList<>();
		List<MatrixColumnRow> baseMatrixColumn = new ArrayList<>();
		List<MatrixColumnRow> baseMatrixRow = new ArrayList<>();
		List<ElementData> listElement = baseData.getElements();


		for (int i = 0; i < dataNeedChecking.size(); i++) {
			ExcelValidatorModel model = dataNeedChecking.get(i);
			System.out.println(model.getType() + " - " + model.getRow() + " - " + model.getColumn());

			switch (model.getType()) {
			case "individual":

				for (int j = 0; j < listElement.size(); j++) {
					if (listElement.get(j).getId() == model.getElementId()) {
						Individual baseIndividual = (Individual) listElement.get(j);
						condition = baseIndividual.getData().getConditions();
						check = ExcelValidator.validateOnType(condition, model.getValue(),
								baseIndividual.getData().getType(), model.getRow(), model.getColumn());
						break;
					}
				}

				if (check.getErrorCode() != 0) {
					errorList.add(check);
				}

				break;
			case "table":
				TableValidatorModel tableModel = (TableValidatorModel) model;
				for (int j = 0; j < listElement.size(); j++) {
					if (listElement.get(j).getId() == tableModel.getElementId()) {
						Table baseTable = (Table) listElement.get(j);
						baseTableColumn = baseTable.getData().getColumns();
						for (int k = 0; k < baseTableColumn.size(); k++) {
							if (tableModel.getId() == baseTableColumn.get(k).getId()) {
								Condition tableCondition = baseTableColumn.get(k).getConditions();
								check = ExcelValidator.validateOnType(tableCondition, tableModel.getValue(),
										baseTableColumn.get(k).getType(), tableModel.getRow(), tableModel.getColumn());
								if (check.getErrorCode() != 0) {
									errorList.add(check);
								}
							}
						}
						break;
					}
				}

				break;
			case "tableHeader":
				TableValidatorModel tableHeaderModel = (TableValidatorModel) model;
				for (int j = 0; j < listElement.size(); j++) {
					if (listElement.get(j).getId() == tableHeaderModel.getElementId()) {
						Table baseTable = (Table) listElement.get(j);
						baseTableColumn = baseTable.getData().getColumns();
						for (int k = 0; k < baseTableColumn.size(); k++) {
							if (tableHeaderModel.getId() == baseTableColumn.get(k).getId()) {
								check = ExcelValidator.checkHeader(baseTableColumn.get(k).getName(),
										tableHeaderModel.getValue(), tableHeaderModel.getRow(),
										tableHeaderModel.getColumn());
								if (check.getErrorCode() != 0) {
									errorList.add(check);
								}
							}
						}
						break;
					}
				}

				break;
			case "matrix":
				MatrixValidatorModel matrixModel = (MatrixValidatorModel) model;
				for (int j = 0; j < listElement.size(); j++) {
					if (listElement.get(j).getId() == matrixModel.getElementId()) {
						Matrix baseMatrix = (Matrix) listElement.get(j);
						baseMatrixValue = baseMatrix.getData().getValues();
						for (int k = 0; k < baseMatrixValue.size(); k++) {
							if (baseMatrixValue.get(k).getColumnId() - baseMatrixValue.get(k).getRowId() == matrixModel
									.getId()) {
								Condition matrixCondition = baseMatrixValue.get(k).getConditions();
								check = ExcelValidator.validateOnType(matrixCondition, matrixModel.getValue(),
										baseMatrixValue.get(k).getType(), matrixModel.getRow(),
										matrixModel.getColumn());
								if (check.getErrorCode() != 0) {
									errorList.add(check);
								}
							}

						}
						break;
					}
				}

				break;
			case "matrixHeader":
				MatrixValidatorModel matrixHeaderModel = (MatrixValidatorModel) model;
				for (int j = 0; j < listElement.size(); j++) {
					if (listElement.get(j).getId() == matrixHeaderModel.getElementId()) {
						Matrix baseMatrix = (Matrix) listElement.get(j);
						baseMatrixColumn = baseMatrix.getData().getColumns();
						for (int k = 0; k < baseMatrixColumn.size(); k++) {
							if (baseMatrixColumn.get(k).getId() == matrixHeaderModel.getId()) {
								check = ExcelValidator.checkHeader(baseMatrixColumn.get(k).getName(),
										matrixHeaderModel.getValue(), matrixHeaderModel.getRow(),
										matrixHeaderModel.getColumn());

								if (check.getErrorCode() != 0) {
									errorList.add(check);
								}
							}

						}
						break;
					}
				}

				break;
			case "matrixRowHeader":
				MatrixValidatorModel matrixRowHeaderModel = (MatrixValidatorModel) model;
				for (int j = 0; j < listElement.size(); j++) {
					if (listElement.get(j).getId() == matrixRowHeaderModel.getElementId()) {
						Matrix baseMatrix = (Matrix) listElement.get(j);
						baseMatrixRow = baseMatrix.getData().getColumns();
						for (int k = 0; k < baseMatrixColumn.size(); k++) {
							if (baseMatrixRow.get(k).getId() == matrixRowHeaderModel.getId()) {
								check = ExcelValidator.checkHeader(baseMatrixRow.get(k).getName(),
										matrixRowHeaderModel.getValue(), matrixRowHeaderModel.getRow(),
										matrixRowHeaderModel.getColumn());

								if (check.getErrorCode() != 0) {
									errorList.add(check);
								}
							}

						}
						break;
					}
				}

				break;
			}

		}

		return errorList;
	}

	public static ValidatorErrorModelException validateOnType(Condition condition, String stringNeedCheck, String type,
			int row, String column) {
		ValidatorErrorModelException error = new ValidatorErrorModelException();
		switch (type) {
		case "text":
			if (!UtilValidator.checkRequired(stringNeedCheck, condition.isRequired())) {
				error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
				error.setErrorMessage(ExcelValidatorConstant.ERROR_TEXT_NULL + row + column);

			} else {
				if (!UtilValidator.checkMaxLength(stringNeedCheck, condition.getMax_length())) {
					error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
					error.setErrorMessage(ExcelValidatorConstant.ERROR_TEXT_LENGTH + row + column);
				}

				if (!UtilValidator.checkMinLength(stringNeedCheck, condition.getMin_length())) {
					error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
					error.setErrorMessage(ExcelValidatorConstant.ERROR_TEXT_LENGTH + row + column);
				}
			}
			break;
		case "number":
			if (!UtilValidator.checkNumberFormat(stringNeedCheck, condition.getFormat().getRegex())) {
				error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
				error.setErrorMessage(ExcelValidatorConstant.ERROR_NUMBER_FORMAT + row + column);
			}
			break;
		case "select":
			if (!UtilValidator.checkValidDataOption(stringNeedCheck, condition.getData())) {
				error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
				error.setErrorMessage(ExcelValidatorConstant.ERROR_SELECT_DATA + row + column);
			}
			break;
		case "date":
			if (!UtilValidator.checkRequired(stringNeedCheck, condition.isRequired())) {
				error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
				error.setErrorMessage(ExcelValidatorConstant.ERROR_DATE_FORMAT + row + column);
			} else {
				if (!UtilValidator.checkDateFormat(stringNeedCheck, condition.getFormat().getRegex())) {
					error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
					error.setErrorMessage(ExcelValidatorConstant.ERROR_DATE_FORMAT + row + column);
				}
			}

			break;
		case "textarea":

			break;
		default:
			break;
		}
		return error;
	}

	public static ValidatorErrorModelException checkHeader(String baseHeader, String header, int row, String column) {
		ValidatorErrorModelException error = new ValidatorErrorModelException();
		if (UtilValidator.checkDataType(header, baseHeader)) {
			error.setErrorCode(UtilValidatorConstant.ERROR_CODE_NULL);
			error.setErrorMessage(ExcelValidatorConstant.ERROR_HEADER + row + column);
		}
		return error;
	}

}
