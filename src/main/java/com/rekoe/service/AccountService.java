package com.rekoe.service;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.TableName;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.trans.Molecule;

import com.rekoe.common.page.Pagination;
import com.rekoe.domain.Account;

@IocBean(fields = { "dao" })
public class AccountService extends BaseService<Account> {
	public AccountService() {
		super();
	}

	public AccountService(Dao dao) {
		super(dao);
	}

	public void createTable() {
		for (int j = 0; j < 10; j++) {
			Molecule<Account> m = new Molecule<Account>() {
				public void run() {
					dao().create(getEntityClass(), false);
				}
			};
			TableName.run(j, m);
		}
	}

	public Account fetch(final String passportid) {
		Molecule<Account> m = new Molecule<Account>() {
			public void run() {
				setObj(dao().fetch(getEntityClass(), Cnd.where("passportid", "=", passportid)));
			}
		};
		TableName.run(num(passportid), m);
		return m.getObj();
	}

	public void insert(final Account account) {
		Molecule<Account> m = new Molecule<Account>() {
			public void run() {
				dao().insert(account);
			}
		};
		TableName.run(num(account.getPassportid()), m);
	}

	protected int num(String uid) {
		Long temp = Math.abs(NumberUtils.isNumber(uid) ? NumberUtils.toLong(uid) : uid.hashCode());
		if (temp >= 10) {
			temp = temp / 10;
			temp = temp % 10;
		}
		return temp.intValue();
	}

	public void update(final Account account) {
		Molecule<Account> m = new Molecule<Account>() {
			public void run() {
				dao().update(account);
			}
		};
		TableName.run(num(account.getPassportid()), m);
	}

	public Pagination getObjListByPager(Integer pageNumber, int index) {
		final Integer page = getPageNumber(pageNumber);
		Molecule<Pagination> m = new Molecule<Pagination>() {
			public void run() {
				Pager pager = dao().createPager(page, 20);
				List<Account> list = dao().query(getEntityClass(), null, pager);
				pager.setRecordCount(dao().count(getEntityClass(), null));
				Pagination pagination = new Pagination(page, 20, pager.getRecordCount(), list);
				setObj(pagination);
			}
		};
		TableName.run(num(index + ""), m);
		return m.getObj();
	}
}
