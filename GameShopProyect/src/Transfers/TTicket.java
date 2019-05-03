package Transfers;

import java.sql.Timestamp;
import java.util.List;

/** 
* @author GameShop
* @generated "UML to Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class TTicket {
	
	private Double _finalPrice;
	private Timestamp _date;
	private Integer _id;
	private Integer _employeeId;
	private List<TProduct> _productsId;
	private String _log;
	
	// CONSTRUCTOR
	
	public TTicket(Integer employeeID, List<Object> productsList) {
		
	}
	
	public Double get_finalPrice() {
		return _finalPrice;
	}
	public void set_finalPrice(Double _finalPrice) {
		this._finalPrice = _finalPrice;
	}
	public Timestamp get_date() {
		return _date;
	}
	public void set_date(Timestamp _date) {
		this._date = _date;
	}
	public Integer get_id() {
		return _id;
	}
	public void set_id(Integer _id) {
		this._id = _id;
	}
	public Integer get_employeeId() {
		return _employeeId;
	}
	public void set_employeeId(Integer _employeeId) {
		this._employeeId = _employeeId;
	}
	public List<TProduct> get_productsId() {
		return _productsId;
	}
	public void set_productsId(List<TProduct> _productsId) {
		this._productsId = _productsId;
	}
	public String getLog() {
		return _log;
	}
	public void setLog(String log)
	{
		this._log = log;
	}
	
	/*public Double getPrice() {
		return null;
	}

	public void getDate() {
	}

	public void setPrice(Double d) {
	}

	public void setDate(Timestamp t) {
	}

	public Integer getId() {
		return null;
	}

	public void setId(Integer i) {
	}

	public Boolean isActivated() {
		return null;
	}

	public void setActivated(Boolean b) {
	}

	public Integer getEmployee() {
		return null;
	}

	public void setEmployee(Integer i) {
	}

	public List getProducts() {
		return null;
	}

	public void addProduct(Integer id) {
	}

	public void removeProduct(Integer id) {
	}
	*/
}