package br.com.devsimples.tarefas.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tarefas")
public class Tarefas {

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
