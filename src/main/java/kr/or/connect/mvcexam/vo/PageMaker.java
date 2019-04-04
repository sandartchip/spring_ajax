package kr.or.connect.mvcexam.vo;

public class PageMaker { 
	
	/* 게시판 페이징을 실제로 담당하는 클래스. */
	/* 게시판 하단에 있는 페이징 부분을 만들기 위해 필요함. */
	// 총 페이지 수, 
	
	private Criteria page_vo;
	private int totalCount; //전체 페이지 수
	private int startPage; // 시작 페이지 
	private int endPage;   // 끝나는 페이지
	private boolean prev;  // 이전 페이지의 사용 여부
	private boolean next;  // 다음 페이지의 사용 여부
	private int displayPageNum = 5; //화면 하단에 표시되는 '페이지'의 갯수. (한 페이지 당 게시글 수 아님 주의)
	
	public void calcData() {
		// 하단에 페이지 생성하는데 필요한 정보들에 대한 계산 작업을 한다.
		// prev, next, startPage, endPage를 계산.
		// page_vo(현재페이지, 페이지당 게시물수) 정보, totalCount만  필요.
		
        endPage = (int) (Math.ceil(page_vo.getPage() / (double) displayPageNum) * displayPageNum);
        
        int tempEndPage = (int) (Math.ceil(totalCount / (double) page_vo.getPagePerNum()));
        
        if (endPage > tempEndPage) {
            endPage = tempEndPage;
        }
        
        startPage = (endPage - displayPageNum) + 1;
        
        if(startPage<1) startPage=1; 
 
        prev = startPage == 1 ? false : true;
        next = endPage * page_vo.getPagePerNum() >= totalCount ? false : true;
        
	}
	public Criteria getPage_vo() {
		return page_vo;
	}
	public void setPage_vo(Criteria page_vo) {
		this.page_vo = page_vo;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() { 
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() { //다음 버튼 생성 여부.
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public int getDisplayPageNum() {
		return displayPageNum;
	}
	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}
}
