package mg.cnaps.services;

//import java.sql.Date;
//import java.util.List;
import mg.cnaps.models.ProspectionMod;


public interface ProspectionService extends CRUDService<ProspectionMod> {
	 long seqDemande();
}
