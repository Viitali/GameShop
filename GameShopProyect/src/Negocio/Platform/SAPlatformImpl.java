package Negocio.Platform;

import java.util.List;

import Integracion.DAO.DAOAbstractFactory;
import Integracion.Platform.DAOPlatform;
import Transfers.TPlatform;


public class SAPlatformImpl implements SAPlatform {

	@Override
	public Integer createPlatform(TPlatform tpla) {
		int id = -1;
		if(tpla != null && !tpla.get_name().trim().equals("")){ //Comprobamos que el transfer no esta vacio, y que hay nombre introducido.
			DAOPlatform daoPlatform = DAOAbstractFactory.getInstance().createDAOPlatform();
			TPlatform tpl = daoPlatform.readByName(tpla.get_name());
			if(tpl == null)
				id = daoPlatform.createPlatform(tpla);
		}
		return id;
	}

	@Override
	public Boolean deletePlatform(Integer id) {
		boolean ret = false;
		if(id != null) {
			DAOPlatform daoPlatform = DAOAbstractFactory.getInstance().createDAOPlatform();
			TPlatform ternif = daoPlatform.readPlatform(id);
			// Si devuelve un transfer significa que existe y por lo tanto se procede a borrarlo
			if(ternif != null && ternif.get_activated())
				ret = daoPlatform.deletePlatform(id);
		}
		return ret;
	}

	@Override
	public Boolean updatePlatform(TPlatform tpla) {
		return (tpla != null && !tpla.get_name().trim().equals("")) ?
				DAOAbstractFactory.getInstance().createDAOPlatform().updatePlatform(tpla) : false;
	}

	@Override
	public TPlatform readPlatform(Integer id) {
		TPlatform ret = null;
		if(id != null){
			DAOPlatform daoPlatform = DAOAbstractFactory.getInstance().createDAOPlatform();
			ret = (TPlatform) daoPlatform.readPlatform(id);
		}
		return ret;
	}

	@Override
	public List<Object> readAllPlatforms() {
		List<Object> platforms = null;
		platforms = DAOAbstractFactory.getInstance().createDAOPlatform().readAllPlatforms();
		return platforms;
	}

	@Override
	public List<Object> readAllProductsOfAPlatform(Integer id) {
		return DAOAbstractFactory.getInstance().createDAOPlatform().readAllProductsOfAPlatform(id);
	}
	
	
}