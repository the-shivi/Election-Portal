package com.example.votingSystem.entity;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserElectionId implements Serializable {
    private String userId;
    private String electionId;
    // hashCode and equals (required for composite keys)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserElectionId)) return false;
        UserElectionId that = (UserElectionId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(electionId, that.electionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, electionId);
    }
}
