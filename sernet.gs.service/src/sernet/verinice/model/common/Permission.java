/*******************************************************************************
 * Copyright (c) 2009 Robert Schuster <r.schuster@tarent.de>.
 * This program is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation, either version 3 
 * of the License, or (at your option) any later version.
 *     This program is distributed in the hope that it will be useful,    
 * but WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.
 *     You should have received a copy of the GNU Lesser General Public 
 * License along with this program. 
 * If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     Robert Schuster <r.schuster@tarent.de> - initial API and implementation
 ******************************************************************************/
package sernet.verinice.model.common;

import java.io.Serializable;
import java.security.Permissions;
import java.util.HashSet;
import java.util.Set;

import sernet.hui.common.connect.ITypedElement;

@SuppressWarnings("serial")
public class Permission implements Serializable, ITypedElement {

	private Integer dbId;
	
	private CnATreeElement cnaTreeElement;
	
	private String role;
	
	private boolean readAllowed;
	
	private boolean writeAllowed;

    public static final String TYPE_ID = "permission";
	
	protected Permission() {
		// Constructor for Hibernate - does intentionally nothing.
	}
	
	 /* (non-Javadoc)
     * @see sernet.hui.common.connect.ITypedElement#getTypeId()
     */
    public String getTypeId() {
        return TYPE_ID;
    }
	
	public Integer getDbId() {
		return dbId;
	}

	public void setDbId(Integer dbId) {
		this.dbId = dbId;
	}

	public CnATreeElement getCnaTreeElement() {
		return cnaTreeElement;
	}

	public void setCnaTreeElement(CnATreeElement cnaTreeElement) {
		this.cnaTreeElement = cnaTreeElement;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isReadAllowed() {
		return readAllowed;
	}

	public void setReadAllowed(boolean readAllowed) {
		this.readAllowed = readAllowed;
	}

	public boolean isWriteAllowed() {
		return writeAllowed;
	}

	public void setWriteAllowed(boolean writeAllowed) {
		this.writeAllowed = writeAllowed;
	}
	
	/**
	 * Creates a {@link Permission} instance and automatically ties it to a
	 * {@link CnATreeElement}.
	 * 
	 * @param treeElement
	 * @param role
	 * @param readAllowed
	 * @param writeAllowed
	 * @return
	 */
	public static Permission createPermission(
			CnATreeElement treeElement, String role, boolean readAllowed, boolean writeAllowed)
	{
		Permission p = new Permission();
		p.setCnaTreeElement(treeElement);
		p.setRole(role);
		p.setReadAllowed(readAllowed);
		p.setWriteAllowed(writeAllowed);
		
		return p;
	}

	/**
	 * Copy a set of permissions to an object.
	 * 
	 * @param cte the target object on which to set the permissions
	 * @param perms the permission currently assigned on the source object
	 * @return newly created set of permissions
	 */
	public static Set<Permission> clonePermissions(CnATreeElement cte, Set<Permission> perms)
	{
		HashSet<Permission> clone = new HashSet<Permission>();
		
		for (Permission p : perms)
		{
			Permission np =
				createPermission(
						cte,
						p.getRole(),
						p.isReadAllowed(),
						p.isWriteAllowed());
			
			clone.add(np);
		}
		
		return clone;
	}
}