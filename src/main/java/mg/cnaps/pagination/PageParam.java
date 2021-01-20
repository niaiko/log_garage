package mg.cnaps.pagination;

public class PageParam { 
	public  int page;
	public int size;
	public int etat;
	
	 
	public int getPage() {
		return page;
	}
	public int getSize() {
		return size;
	}
	 
	public void setPage(int page) {
		this.page = page;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getEtat() {
		return etat;
	}
	public void setEtat(int etat) {
		this.etat = etat;
	}
	
}
