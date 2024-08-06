package br.com.devsimples.tarefas.repository;

import br.com.devsimples.tarefas.entity.Tarefas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefaRepository extends JpaRepository<Tarefas, Long> {

}
