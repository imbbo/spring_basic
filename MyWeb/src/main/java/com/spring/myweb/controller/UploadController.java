package com.spring.myweb.controller;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/fileupload")
public class UploadController {
	
	@GetMapping("/upload")
	public void form() {}
	
	@PostMapping("/upload_ok")
	public void upload(MultipartFile file) {
		String fileRealName = file.getOriginalFilename(); //파일원본명
		long size = file.getSize(); //파일크기
		
		log.info("파일명 : " + fileRealName);
		log.info("파일크기 : " + size+"bytes");
		/*
		 사용자가 첨부한 파일은 DB에 저장하는 것을 선호하지 않습니다.
		 DB용량을 파일첨부하는건 비용적으로 힘들다
		 파일전용 서버 구축해서 경로 지정
		 타언어를 지원하지 않는 환경에서는 문제 될수 있음(리눅수)
		 고유한 랜덤 문자를 통해 파일명 새로 만들어줍니ㅏㄷ.
		 */
		UUID uuid = UUID.randomUUID();
		log.info("uuid : " +uuid.toString());
		String[] uuids = uuid.toString().split("-");
		log.info("생성된 고유 문자열(파일명): " +uuids[0]);
		String fileExtension = fileRealName.substring(fileRealName.lastIndexOf("."),fileRealName.length());
		log.info("확장자명 : "+fileExtension);
		//DB에는 파일경로를 저장, 실제 파일은 서버컴퓨터의 로컬 경로에 저장하는 방식...
		String uploadFolder = "C:/test/upload";
		File f = new File(uploadFolder);
		if(!f.exists()) {
			log.info("폴더가 존재하지 않음");
			f.mkdirs();//경로조건이 여러개 한개면 s뺀다			
		}
		File saveFile = new File(uploadFolder+"/"+uuids[0]+fileExtension);
		try {
			//매개값으로 받은 첨부 파일을 지정한 로컬 경로에 보내는 메서드.
			file.transferTo(saveFile);
		} catch (Exception e) {			
			e.printStackTrace();
		} 		
	}
	
	@PostMapping("/upload_ok2")
	public String upload2(MultipartHttpServletRequest files) {
		
		// 첨부파일이 여러 개 전달되는 경우 1 
		String uploadFolder = "C:/test/upload";
		
		files.getFiles("files");
		
		List<MultipartFile> list = files.getFiles("files");
		
		for(MultipartFile m : list) {
			try {
				String fileRealName = m.getOriginalFilename();
				long size = m.getSize();
				
				File saveFile = new File(uploadFolder + "/" + fileRealName);
				
				m.transferTo(saveFile);
				
			} catch (Exception e) {
					e.printStackTrace();
			}
			
		}
		return "fileupload/upload_ok";
	}
	
	@PostMapping("/upload_ok3")
	public String upload(@RequestParam("file") List<MultipartFile> list) {
	
		String uploadFolder = "C:/test/upload";
		
		for(MultipartFile m : list) {
			try {
				if(m.getSize() == 0) break;
				
				String fileRealName = m.getOriginalFilename();
				long size = m.getSize();				
				
				File saveFile = new File(uploadFolder + "/" + fileRealName);
				
				m.transferTo(saveFile);
				
			} catch (Exception e) {
					e.printStackTrace();
			}
		}
		
		return "fileupload/upload_ok";
	}
	
	
	
	
	
}
