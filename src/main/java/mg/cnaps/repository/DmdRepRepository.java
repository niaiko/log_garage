package mg.cnaps.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
//import java.sql.Date;
//import java.util.List;
//
//import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import mg.cnaps.models.DmdRepMod;

public interface DmdRepRepository extends JpaRepository<DmdRepMod, Integer> {
	@Query(value = "select nextval('\"RFM\".demande_reparation_id_demande_rep_seq')", nativeQuery = true)
	long seqDemande();

//  recherche
//	@Query("select u from CourrierMod u where (lower(u.destinataire) like '%'||lower(?1)||'%' or ?1 is null ) and (u.dateInsert = ?2 or cast(?2 as date) is null)")
//	List<CsLogMod> getByDestinataireDateCou(String destinataire,Date dateInsert);
	@Transactional
	@Modifying
	@Query("update DmdRepMod u set u.idetat = ?2, u.observation = ?3  where u.iddmdrep = ?1")
	public void updateetatdmd(int iddmd, int newstatrep, String observation);

	Page<DmdRepMod> findByIdetat(Pageable page, int etat);

	@Query("select u from DmdRepMod u where idetat = 1")
	List<DmdRepMod> listeinstance();

	@Query("select u from DmdRepMod u where idetat = 1")
	Page<DmdRepMod> listeinstancePage(Pageable pageable);

	@Query("select u from DmdRepMod u where idetat = 2")
	List<DmdRepMod> listevalide();

	@Query("select u from DmdRepMod u where idetat = 3")
	List<DmdRepMod> listeencours();

	@Query("select u from DmdRepMod u where idetat = 4")
	List<DmdRepMod> listeacheve(Pageable pageable);

	@Query("select u from DmdRepMod u where idetat = 5")
	List<DmdRepMod> listeannule(Pageable pageable);

	@Transactional
	@Modifying
	@Query("update DmdRepMod u set u.observation = ?1  where u.iddmdrep = ?2")
	void updateobservationgarage(String observation, Integer iddemande);
}
