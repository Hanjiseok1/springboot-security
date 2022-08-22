package com.study.securty_jiseok.service.notice;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.study.securty_jiseok.web.dto.notice.AddNoticeReqDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Value("${file.path}")
	private String filePath;
	
	public int addNotice(AddNoticeReqDto addNoticeReqDto) throws Exception{
		
		String originalFilename = addNoticeReqDto.getFile().get(0).getOriginalFilename();
		
		if(originalFilename.isBlank()) {
			
		}else {
			String tempFilename = UUID.randomUUID().toString().replaceAll("-", "") + "_" + originalFilename;
			log.info(tempFilename);
		}
		return 0;
	}
	
}