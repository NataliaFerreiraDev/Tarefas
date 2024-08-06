package br.com.devsimples.tarefas.service;

import br.com.devsimples.tarefas.entity.Tarefa;
import br.com.devsimples.tarefas.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public Tarefa criar(Tarefa tarefa){
        return tarefaRepository.save(tarefa);
    }

    public List<Tarefa> listar(){
        return tarefaRepository.findAll();
    }

    public Tarefa alterar(Tarefa tarefa){
        return tarefaRepository.save(tarefa);
    }

    public void excluir(Long id){
        tarefaRepository.deleteById(id);
    }


}
