/*******************************************************************************
 * Copyright (c) 2015 Daniel Murygin.
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
 *     Daniel Murygin <dm[at]sernet[dot]de> - initial API and implementation
 ******************************************************************************/
package sernet.verinice.service.linktable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import sernet.hui.common.connect.EntityType;
import sernet.hui.common.connect.HUITypeFactory;
import sernet.hui.common.connect.PropertyType;
import sernet.hui.common.connect.URLUtil;
import sernet.verinice.interfaces.graph.VeriniceGraph;
import sernet.verinice.model.common.CnATreeElement;

/**
 * Path element in a column path definition which loads a property of an element.
 * Delimiter for this path element is: IPathElement.DELIMITER_PROPERTY (.)
 * See GenericDataModel for a description of column path definitions.
 *
 * @see GenericDataModel
 * @author Daniel Murygin <dm[at]sernet[dot]de>
 */
public class PropertyElement implements IPathElement {

    private static final Logger LOG = Logger.getLogger(PropertyElement.class);

    private String propertyTypeId;
    private String propertyValue;
    private Map<String,Map<String, Object>> result;
    private String alias;

    public PropertyElement() {
        super();
        result = new HashMap<>();
    }

    public PropertyElement(String propertyTypeId) {
        this.propertyTypeId = propertyTypeId;
        result = new HashMap<>();
    }

    public String getPropertyTypeId() {
        return propertyTypeId;
    }

    public void setPropertyTypeId(String propertyTypeId) {
        this.propertyTypeId = propertyTypeId;
    }

    /* (non-Javadoc)
     * @see sernet.verinice.report.service.impl.dynamictable.IPathElement#setTypeId(java.lang.String)
     */
    @Override
    public void setTypeId(String typeId) {
        propertyTypeId = typeId;

    }

    /* (non-Javadoc)
     * @see sernet.verinice.report.service.impl.dynamictable.IPathElement#load(sernet.verinice.model.common.CnATreeElement, sernet.verinice.interfaces.graph.VeriniceGraph)
     */
    @Override
    public void load(CnATreeElement element, VeriniceGraph graph) {
        String parentId = String.valueOf(element.getDbId());
        propertyValue = getPropertyValue(element, propertyTypeId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put(parentId, propertyValue);
        if (LOG.isDebugEnabled()) {
            LOG.debug(element.getTitle() + "(" + parentId + ")." + propertyTypeId + " = " + propertyValue + " loaded");
        }
        getResult().put(parentId, resultMap);
    }

    /* (non-Javadoc)
     * @see sernet.verinice.report.service.impl.dynamictable.IPathElement#createValueMap(java.util.Map, java.lang.String)
     */
    @Override
    public Map<String, String> createResultMap(Map<String, String> map, String key) {
        Set<String> childKeySet = getResult().keySet();
        for (String childKey : childKeySet) {
            if(key.endsWith(childKey)) {
                Map<String,Object> resultMap = getResult().get(childKey);
                Set<String> resultKeySet = getResult().keySet();
                for (String resultKey : resultKeySet) {
                    if(key.endsWith(resultKey)) {
                        String value = (String) resultMap.get(resultKey);
                        map.put(key, value);
                    }
                }
            }
        }
        return map;
    }

    /* (non-Javadoc)
     * @see sernet.verinice.report.service.impl.dynamictable.IPathElement#getResult()
     */
    @Override
    public Map<String,Map<String, Object>> getResult() {
        return result;
    }

    private String getPropertyValue(CnATreeElement element, String propertyId) {
        String value = element.getEntity().getSimpleValue(propertyId);
        PropertyType propertyType = getPropertyType(element.getTypeId(), propertyId);
        if(propertyType!=null && propertyType.isURL()) {
            value = URLUtil.getHref(value);
        }
        return value;
    }

    private PropertyType getPropertyType(String elementId, String propertyId) {
        return getEntityType(elementId).getPropertyType(propertyId);
    }

    public String getTypeId() {
        return propertyTypeId;
    }

    private EntityType getEntityType(String elementId) {
        return HUITypeFactory.getInstance().getEntityType(elementId);
    }

    /**
     * @return the propertyValue
     */
    public String getPropertyValue() {
        return propertyValue;
    }

    /**
     * @param propertyValue the propertyValue to set
     */
    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    /* (non-Javadoc)
     * @see sernet.verinice.report.service.impl.dynamictable.IPathElement#getChild()
     */
    @Override
    public IPathElement getChild() {
        return null;
    }

    /* (non-Javadoc)
     * @see sernet.verinice.report.service.impl.dynamictable.IPathElement#setChild(sernet.verinice.report.service.impl.dynamictable.IPathElement)
     */
    @Override
    public void setChild(IPathElement child) {
        // A property element never has childs
    }

    /* (non-Javadoc)
     * @see sernet.verinice.service.linktable.IPathElement#getAlias()
     */
    @Override
    public String getAlias() {
        return alias;
    }

    /* (non-Javadoc)
     * @see sernet.verinice.service.linktable.IPathElement#setAlias(java.lang.String)
     */
    @Override
    public void setAlias(String alias) {
        this.alias = alias;
        if(getChild()!=null) {
            getChild().setAlias(alias);
        }
    }

}
