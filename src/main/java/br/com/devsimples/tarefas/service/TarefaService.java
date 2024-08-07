package br.com.devsimples.tarefas.service;

import br.com.devsimples.tarefas.entity.Tarefa;
import br.com.devsimples.tarefas.repository.TarefaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TarefaService {

    private final TarefaRepository tarefaRepository;

    public TarefaService(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }

    public Tarefa criar(Tarefa tarefa){

        Tarefa tarefaCriada = new Tarefa();
        tarefaCriada.setCriacao(LocalDateTime.now());
        tarefaCriada.setAtualizacao(LocalDateTime.now());
        tarefaCriada.setFinalizado(false);
        tarefaCriada.setTitulo(tarefa.getTitulo());
        tarefaCriada.setDescricao(tarefa.getDescricao());
        tarefaCriada.setPrioridade(tarefa.getPrioridade());

        return tarefaRepository.save(tarefaCriada);
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
