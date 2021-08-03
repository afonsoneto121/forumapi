package com.br.dio.forumapi.mapper;

import com.br.dio.forumapi.dto.request.TopicDTO;
import com.br.dio.forumapi.entity.Topic;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TopicMapper {
    public TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);

    @Mapping(target = "dateCreation", source = "dateCreation", dateFormat = "dd-MM-yyyy")
    Topic toModel(TopicDTO topicDTO);
    TopicDTO toDTO(Topic topic);
}
