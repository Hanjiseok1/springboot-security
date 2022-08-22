package com.study.securty_jiseok.service.notice;

import com.study.securty_jiseok.web.dto.notice.AddNoticeReqDto;

public interface NoticeService {
	
	public int addNotice(AddNoticeReqDto addNoticeReqDto) throws Exception;
	
}
