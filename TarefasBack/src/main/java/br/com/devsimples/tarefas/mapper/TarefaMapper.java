package br.com.devsimples.tarefas.mapper;

import br.com.devsimples.tarefas.entity.Tarefa;
import br.com.devsimples.tarefas.entity.TarefaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TarefaMapper {

    @Mapping(target = "dataCriacao", ignore = true)
    @Mapping(target = "dataUltimaAtualizacao", ignore = true)
    @Mapping(target = "dataConclusao", ignore = true)
    Tarefa toEntity(TarefaDTO dto);

    TarefaDTO toDTO(Tarefa entity);

}
