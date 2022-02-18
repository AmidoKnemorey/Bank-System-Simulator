package DataBaseConnection;

public interface DaoInterface<Client> {

    public boolean findClientInDB(int accountNumber);

    public Client getClientFromDB(int accountNumber);

    public void updateClientInDB(Client client);


}