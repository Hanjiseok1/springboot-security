package com.study.securty_jiseok.service.notice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.study.securty_jiseok.domain.notice.Notice;
import com.study.securty_jiseok.domain.notice.NoticeFile;
import com.study.securty_jiseok.domain.notice.NoticeRepository;
import com.study.securty_jiseok.web.dto.notice.AddNoticeReqDto;
import com.study.securty_jiseok.web.dto.notice.GetNoticeListResponseDto;
import com.study.securty_jiseok.web.dto.notice.GetNoticeResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {
	
	@Value("${file.path}")
	private String filePath;
	
	private final NoticeRepository noticeRepository;
	
	@Override
	public List<GetNoticeListResponseDto> getNoticeList(int page, String searchFlag, String searchValue) throws Exception {
		int index = (page - 1) * 10;
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("index", index);
		map.put("search_flag", searchFlag);
		map.put("search_value", searchValue == null ? "" : searchValue);
		
		List<GetNoticeListResponseDto> list = new ArrayList<GetNoticeListResponseDto>();
		
		noticeRepository.getNoticeList(map).forEach(notice -> {
			list.add(notice.toListDto());
		});
		
		return list;
	}
	
	public int addNotice(AddNoticeReqDto addNoticeReqDto) throws Exception{
		Predicate<String> predicate = (filename) -> !filename.isBlank();
		
		Notice notice = null;
		
		notice = Notice.builder()
				.notice_title(addNoticeReqDto.getNoticeTitle())
				.user_code(addNoticeReqDto.getUserCode())
				.notice_content(addNoticeReqDto.getIr1())
				.build();
		
		noticeRepository.saveNotice(notice);
		
		if(predicate.test(addNoticeReqDto.getFile().get(0).getOriginalFilename())) {
			List<NoticeFile> noticeFiles = new ArrayList<NoticeFile>();
			
			for(MultipartFile file : addNoticeReqDto.getFile()){
				String originalFilename = file.getOriginalFilename();
				String tempFilename = UUID.randomUUID().toString().replaceAll("-", "") + "_" + originalFilename;
				log.info(tempFilename);
				
				Path uploadPath = Paths.get(filePath, "notice/" + tempFilename);
				
				File f = new File(filePath + "notice");
				if(!f.exists()) {
					f.mkdirs();
				}
				
				try {
					Files.write(uploadPath, file.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				noticeFiles.add(NoticeFile.builder().notice_code(notice.getNotice_code()).file_name(tempFilename).build());
		} 
			noticeRepository.saveNoticeFiles(noticeFiles);
		}
		
		return notice.getNotice_code();
	}
	
	@Override
	public GetNoticeResponseDto getNotice(String flag, int noticeCode) throws Exception {
		GetNoticeResponseDto getNoticeResponseDto = null;
		
		Map<String, Object> reqmap = new HashMap<String, Object>();
		reqmap.put("flag", flag);
		reqmap.put("notice_code", noticeCode);
		noticeRepository.countIncrement(reqmap);
		
		List<Notice> notices = noticeRepository.getNotice(reqmap);
		System.out.println(notices);
		if(!notices.isEmpty()) {
			List<Map<String, Object>> downloadFiles = new ArrayList<Map<String,Object>>();
			notices.forEach(notice -> {
				Map<String, Object> fileMap = new HashMap<String, Object>();
				String fileName = notice.getFile_name();
				System.out.println(fileName);
				
				if(fileName != null) {
					fileMap.put("fileCode", notice.getFile_code());				
					fileMap.put("fileOriginName", fileName.substring(fileName.indexOf("_") + 1));				
					fileMap.put("fileTempName", fileName);				
				}
				downloadFiles.add(fileMap);
			});
			
			Notice firstNotice = notices.get(0);
			
			getNoticeResponseDto = GetNoticeResponseDto.builder()
					.noticeCode(firstNotice.getNotice_code())
					.noticeTitle(firstNotice.getNotice_title())
					.userCode(firstNotice.getUser_code())
					.userId(firstNotice.getUser_id())
					.createDate(firstNotice.getCreate_date().format(DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss")))
					.noticeCount(firstNotice.getNotice_count())
					.noticeContent(firstNotice.getNotice_content())
					.downloadFiles(downloadFiles)
					.build();
		}
		
		return getNoticeResponseDto;
	}
	
}
