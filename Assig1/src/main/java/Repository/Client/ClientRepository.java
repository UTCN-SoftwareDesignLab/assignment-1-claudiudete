package Repository.Client;

import Repository.EntityNotFoundException;
import model.Client;

public interface ClientRepository {

    Client findByID(Long id) throws EntityNotFoundException;
    boolean save(Client client);
    boolean updateClient(Client client);
    boolean deleteClient(Long client_id);
    Client findByName(String name);
}
