package com.study.securty_jiseok.service.notice;

import com.study.securty_jiseok.web.dto.notice.AddNoticeReqDto;
import com.study.securty_jiseok.web.dto.notice.GetNoticeResponseDto;

public interface NoticeService {
	
	public int addNotice(AddNoticeReqDto addNoticeReqDto) throws Exception;
	public GetNoticeResponseDto getNotice(String flag, int noticeCode) throws Exception;
}
