package mg.cnaps.models;

import java.util.List;

public class ParamDmdRep {

	private int page;
	private int nbPage;
	private List<DmdRepMod> l;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getNbPage() {
		return nbPage;
	}

	public void setNbPage(int nbPage) {
		this.nbPage = nbPage;
	}

	public List<DmdRepMod> getL() {
		return l;
	}

	public void setL(List<DmdRepMod> l) {
		this.l = l;
	}

}
