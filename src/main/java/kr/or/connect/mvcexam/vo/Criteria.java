package kr.or.connect.mvcexam.vo;


public class Criteria {
	/* 게시글 조회 쿼리에 전달할 Page관련 파라미터를 담게 될 클래스 */
	/* 특정 페이지를 표시하게 됨 */
	
	int curPage;    //현재 페이지 번호  (게시글 번호 X)
	int pagePerNum; //1페이지당 몇갠지
	
	public Criteria() {
		this.curPage = 1;
		this.pagePerNum = 3;
	} 
	public int getPage() {
		return curPage;
	}

	public void setCurPage(int page) {
		if(page <= 0) {
			this.curPage = 1; //페이지 번호 잘못 들어온 경우
			return;
		}
		this.curPage = page; //현재 페이지가 몇 번인지 셋팅
	}

	public int getPagePerNum() {
		return pagePerNum;
	}

	public void setPagePerNum(int pagePerNum) { 
		if(pagePerNum <=0 || pagePerNum >100) { //예외처리
			System.out.println(" 페이지 오류ㅇㅇㅇ");
			this.pagePerNum = 10;
			return;
		}
		this.pagePerNum = pagePerNum;
	}
}