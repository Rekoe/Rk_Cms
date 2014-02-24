package com.rekoe.service;

import java.util.List;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.random.R;

import com.rekoe.domain.Client;

@IocBean(fields = { "dao" })
public class ClientService extends BaseService<Client> {

	public Client createClient(Client client) {
		client.setClientId(R.UU16());
		client.setClientSecret(R.UU16());
		return dao().insert(client);
	}

	public Client updateClient(Client client) {
		dao().update(client);
		return client;
	}

	public void deleteClient(Long clientId) {
		dao().delete(getEntityClass(), clientId);
	}

	public Client findOne(Long clientId) {
		return dao().fetch(getEntityClass(), clientId);
	}

	public List<Client> findAll() {
		return dao().query(getEntityClass(), null);
	}

	public Client findByClientId(String clientId) {
		return dao().fetch(getEntityClass(), Cnd.where("clientId", "=", clientId));
	}

	public Client findByClientSecret(String clientSecret) {
		return dao().fetch(getEntityClass(), Cnd.where("clientSecret", "=", clientSecret));
	}
}
