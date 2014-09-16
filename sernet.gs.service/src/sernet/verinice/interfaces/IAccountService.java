/*******************************************************************************
 * Copyright (c) 2014 Benjamin Weißenfels.
 *
 * This program is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation, either version 3 
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,    
 * but WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. 
 * If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     Benjamin Weißenfels <bw[at]sernet[dot]de> - initial API and implementation
 ******************************************************************************/
package sernet.verinice.interfaces;

import java.util.List;
import java.util.Set;

import sernet.verinice.model.common.accountgroup.AccountGroup;
import sernet.verinice.model.common.configuration.Configuration;

/**
 * 
 * 
 * @author Benjamin Weißenfels <bw[at]sernet[dot]de>
 * 
 */
public interface IAccountService {

    public List<Configuration> findAccounts(IAccountSearchParameter parameter);

    public void delete(Configuration account);

    public void deactivate(Configuration account);

    public List<Configuration> listAccounts();

    public Set<Configuration> addRole(Set<Configuration> configurations, String role);

    public Set<Configuration> deleteRole(Set<Configuration> configurations, String role);

    public List<AccountGroup> listGroups();

    public void deleteAccountGroup(String name);

    public void deleteAccountGroup(AccountGroup group);

    public AccountGroup createAccountGroup(String name);

    public void saveAccountGroups(Set<String> accountGroupNames);

}