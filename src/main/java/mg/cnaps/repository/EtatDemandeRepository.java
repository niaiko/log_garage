package mg.cnaps.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
//import java.sql.Date;
//import java.util.List;
//
//import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mg.cnaps.models.EtatDemandeMod;

public interface EtatDemandeRepository extends JpaRepository<EtatDemandeMod, Integer> {
	@Query(value = "select nextval('\"RFM\".seqdemande')", nativeQuery = true)
	long seqDemande();

//  recherche
//	@Query("select u from CourrierMod u where (lower(u.destinataire) like '%'||lower(?1)||'%' or ?1 is null ) and (u.dateInsert = ?2 or cast(?2 as date) is null)")
//	List<CsLogMod> getByDestinataireDateCou(String destinataire,Date dateInsert);
	List<EtatDemandeMod> findByIdDmdRep(Integer iddemande);

	@Query("select u from EtatDemandeMod u where (u.idEtat = ?1 or ?1 = 0) and (u.date = ?2 or cast(?2 as date) is null) and (u.matrVehi = ?3 or ?3= '')")
	List<EtatDemandeMod> rechetatdmdgarage(int idetat, Date datevalidation, String matrVehi);

	@Query("select u from EtatDemandeMod u ")
	List<EtatDemandeMod> findalleatadmd(Pageable pageable);
}
