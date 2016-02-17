package vn.com.splussoftware.sms.utils.service.jsonhandler;

import java.util.ArrayList;
import java.util.List;

public class DataObject {
	public List<ElementData> elements = new ArrayList<ElementData>();

	public List<ElementData> getElements() {
		return elements;
	}

	public void setElements(List<ElementData> elements) {
		this.elements = elements;
	}

	public void add(ElementData elementData) {
		this.getElements().add(elementData);
	}

	// TuanHMA find element with specific id
	public ElementData find(int id) {
		ElementData ele = null;
		for (ElementData eObj : elements) {
			if (eObj.getId() == id) {
				ele = eObj;
				break;
			}
		}
		return ele;

	}

	// TuanHMA replace element
	public boolean change(ElementData element) {
		int i = 0;
		for (ElementData eObj : elements) {
			System.out.println("id:" + eObj.getId());
			if (eObj.getId() == element.getId()) {
				elements.set(i, element);
				return true;
			}
			i++;
		}
		return false;
	}

	public String getOnId(int id) {
		for (int i = 0; i < elements.size(); i++) {
			if (elements.get(i).getId() == id) {
				return elements.get(i).getName();
			}
		}
		return "";
	}
}
