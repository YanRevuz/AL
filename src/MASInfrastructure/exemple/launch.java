package MASInfrastructure.exemple;


// interface IMessage : getter et setter sans attribut

import MASInfrastructure.Agent.InfraAgent;
import MASInfrastructure.Infrastructure;
import MASInfrastructure.State.LifeCycle;

public class launch {

    public static void main(String[] args) {
        Infrastructure i = new Infrastructure();  // un scheduler et un annuaire
        CommunicationParMessage maCom= new CommunicationParMessage(i);

        // creation d'un agent de l'application et lien avec infra
        MonAgent a1=new MonAgent("a1");

        InfraAgent infraA1=i.createInfrastructureAgent(new LifeCycle(a1.getPerception()), maCom);
        // la création ajoute l'agent dans l'infrastructure

        a1.setInfraAgent(infraA1);
        a1.getPerception().setInfraAgent(infraA1);
        a1.getDecision().setInfraAgent(infraA1);
        a1.getDecision().setCommunication(i);



        // creation d'un agent de l'application et lien avec infra
        MonAgent a2=new MonAgent("a2");

        a2.getDecision().setCommunication(i);


        InfraAgent infraA2=i.createInfrastructureAgent(new LifeCycle(a2.getPerception()), maCom);

        a2.setInfraAgent(infraA2);
        a2.getPerception().setInfraAgent(infraA2);
        a2.getDecision().setInfraAgent(infraA2);

        i.startScheduling();
  }
}
