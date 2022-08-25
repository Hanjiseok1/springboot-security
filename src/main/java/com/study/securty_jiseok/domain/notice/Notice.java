package com.study.securty_jiseok.domain.notice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.study.securty_jiseok.web.dto.notice.GetNoticeListResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Notice {
	private int notice_code;
	private String notice_title;
	private int user_code;
	private String user_id;
	private String notice_content;
	private int notice_count;
	private int file_code;
	private String file_name;
	private LocalDateTime create_date;
	
	private int total_notice_count;
	
	public GetNoticeListResponseDto toListDto() {
		return GetNoticeListResponseDto.builder()
				.noticeCode(notice_code)
				.noticeTitle(notice_title)
				.userId(user_id)
				.createDate(create_date.format(DateTimeFormatter.ofPattern("yyyy-mm-dd")))
				.noticeCount(notice_count)
				.totalNoticeCount(total_notice_count)
				.build();
	}
}











