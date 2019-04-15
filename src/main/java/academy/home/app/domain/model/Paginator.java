package academy.home.app.domain.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class Paginator<T> {
	
	private String url;
	private Page<T> page;
	
	private int totalOfPages;  		// Total de páginas
	private int elementsPerPage;    // Total de Elementos por página
	
	private int currentPage;
	private int nextPage;
	
	List<PageItem> pages;
	
	public Paginator(String url, Page<T> page) {
		this.url = url;
		this.page = page;
		pages = new ArrayList<>();
		
		elementsPerPage = this.page.getSize();
		totalOfPages = this.page.getTotalPages();
		currentPage = this.page.getNumber() + 1; // numero da página
		
		// 200 items, 10 itens, 20 paginas
		
		int de, para;
		if(totalOfPages <= elementsPerPage) { 
			de = 1; 		
			para = totalOfPages;
		}else {
			if(currentPage <= elementsPerPage / 2) {  // Pra quando está antes da metade
				de = 1;
				para = elementsPerPage;
				
			
			}else if(currentPage >= totalOfPages - elementsPerPage / 2) {
				de = totalOfPages - elementsPerPage + 1;
				para  = elementsPerPage;
			}else {
				de = currentPage - elementsPerPage / 2;
				para = elementsPerPage;
				
			}
			
		}
		
		for (int i = 0; i < para; i++) {
			pages.add(new PageItem(de + i, currentPage == de + i ));
		}
		
	}

	
	public boolean isFirst() {
		return page.isFirst();
	}
	
	public boolean isHasNext() {
		return page.hasNext();
	}
	

	public boolean isHasPrevious() {
		return page.hasPrevious();
	}
	
	public boolean isLast() {
		return page.isLast();
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getTotalOfPages() {
		return totalOfPages;
	}

	public void setTotalOfPages(int totalOfPages) {
		this.totalOfPages = totalOfPages;
	}

	public int getElementsPerPage() {
		return elementsPerPage;
	}

	public void setElementsPerPage(int elementsPerPage) {
		this.elementsPerPage = elementsPerPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getNextPage() {
		return nextPage;
	}

	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}

	public List<PageItem> getPages() {
		return pages;
	}

	public void setPages(List<PageItem> pages) {
		this.pages = pages;
	}

	
	
	public static void main(String[] args) {
		
		
	}
}
