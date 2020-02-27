package MASInfrastructure.Agent;

import java.util.UUID;

public class InfraAgentReference {
    private final UUID InternalReference;

    public InfraAgentReference() {
        InternalReference = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        InfraAgentReference that = (InfraAgentReference) o;

        return InternalReference.equals(that.InternalReference);
    }


    public UUID getInternalReference() {
        return InternalReference;
    }

    @Override
    public int hashCode() {
        return InternalReference.hashCode();
    }

    @Override
    public String toString() {
        return "INFRA.IDAgent{" + this.InternalReference + '}';
    }
}
