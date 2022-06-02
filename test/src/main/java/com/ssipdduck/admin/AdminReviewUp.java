package com.ssipdduck.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.ssipdduck.DTO.AniRecomDTO;
import com.ssipdduck.util.Util;

/**
 * Servlet implementation class AdminReviewUp
 */
@WebServlet("/adminreviewup")
public class AdminReviewUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminReviewUp() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	if(Util.str2Int(request.getParameter("a_no"))){
			int a_no = Integer.parseInt(request.getParameter("a_no"));
			if(request.getParameter("a_no") !=null) {
				AdminboardDAO dao = new AdminboardDAO();
				AniRecomDTO dto = dao.detail(a_no);
				
				RequestDispatcher rd = request.getRequestDispatcher("/adminreviewupdate.jsp");
				request.setAttribute("dto", dto);
				rd.forward(request, response);
			}
    	}
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String url = request.getRealPath("/img/upload");
		MultipartRequest multi = new MultipartRequest(request,url,10*1024*1024,"UTF-8",new DefaultFileRenamePolicy());		
		request.setCharacterEncoding("UTF-8");
		
		if(Util.str2Int(multi.getParameter("a_no"))){
			int a_no = Integer.parseInt(multi.getParameter("a_no"));
			if(multi.getParameter("a_no") !=null) {
				AdminboardDAO dao = new AdminboardDAO();
				AniRecomDTO dto = new AniRecomDTO();
				dto.setA_no(a_no);
				dto.setA_aired(multi.getParameter("a_aired"));
				dto.setA_category(multi.getParameter("a_category"));
				dto.setA_content(multi.getParameter("a_content"));
				dto.setA_epi(Integer.parseInt(multi.getParameter("a_epi")));
				dto.setA_file(multi.getFilesystemName("file"));
				dto.setA_orifile(multi.getOriginalFileName("file"));
				dto.setA_studio(multi.getParameter("a_studio"));
				dto.setA_title(multi.getParameter("a_title"));
				dto.setA_type(multi.getParameter("a_type"));
				dto.setA_writer(multi.getParameter("a_writer"));
		
				dao.reviewup(dto);
				
				response.sendRedirect("./adminreview");
			}
	}

	}
}
