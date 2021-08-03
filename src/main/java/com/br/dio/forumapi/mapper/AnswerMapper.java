package com.br.dio.forumapi.mapper;

import com.br.dio.forumapi.dto.request.AnswerDTO;
import com.br.dio.forumapi.entity.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AnswerMapper {
    AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);

    Answer toModel(AnswerDTO answerDTO);
}
