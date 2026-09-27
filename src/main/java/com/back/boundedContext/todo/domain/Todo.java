package com.back.boundedContext.todo.domain;

import com.back.boundedContext.member.domain.Member;
import com.back.global.entity.BaseIdAndTime;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "TODO")
@Getter
public class Todo extends BaseIdAndTime {

    @ManyToOne(fetch = LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Member author;

    @Column(nullable = false, length = 200)
    private String title;

    private State state;

    private LocalDateTime completedAt;

    public Todo(Member author, String title) {
        this.author = author;
        this.title = title;
        this.state = State.ACTIVE;
    }

    public void changeTitle(String title) {
        this.title = title;
    }

    public void changeDone(boolean completed) {
        this.completedAt = completed ? LocalDateTime.now() : null;
    }

    public void delete() {
        this.state = State.DROPPED;
    }

    enum State {
        ACTIVE, DROPPED
    }

}
