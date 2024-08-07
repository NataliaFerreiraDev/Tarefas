package br.com.devsimples.tarefas.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tarefas")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descricao;

    private int prioridade;

    private boolean finalizado;

    private LocalDateTime criacao;

    private LocalDateTime atualizacao;

    private LocalDateTime conclusao;

}
