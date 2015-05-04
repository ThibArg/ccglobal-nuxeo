/*
 * (C) Copyright ${year} Nuxeo SA (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Thibaud ARguillere
 */

package ccglobal.utils;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentModelList;
import org.nuxeo.ecm.core.api.impl.SimpleDocumentModel;
import org.nuxeo.ecm.platform.ui.web.api.NavigationContext;

/**
 * In the "field" value of the widget, use:
 * #{ccGlobalUtils.getChildForTitle('platas.jpg') == null ? null : ccGlobalUtils.getFoundChild()}
 * or
 * #{ccGlobalUtils.getChildForTitle('Video1.avi') == null ? null : ccGlobalUtils.getFoundChild()}
 * 
 * NOTICE: If the child is not found, we cannot return null, or just new SimpleDocumentModel(),
 * because the beans that come with the widgets (video_player for example) expect a real nuxeo
 * Document to exist. This is why we have to ue a ternary operator in the expression, and some
 * optimization in the Bean.
 */
@Name("ccGlobalUtils")
@Scope(ScopeType.EVENT)
public class CCGlobalUtilsBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Log log = LogFactory.getLog(CCGlobalUtilsBean.class);

    @In(create = true, required = false)
    protected transient CoreSession documentManager;

    @In(create = true)
    protected NavigationContext navigationContext;

    protected DocumentModel theChild = null;
    
    protected String lastParentPath = "";
    
    protected String lastTitle = "";
    
    public DocumentModel getChildForTitle(String inTitle) {
        
        DocumentModel ticket = navigationContext.getCurrentDocument();
        
        if(lastParentPath.equals(ticket.getPathAsString()) && lastTitle.equals(inTitle)) {
            return theChild;
        }
        
        theChild = null;

        DocumentModelList children = documentManager.getChildren(ticket.getRef());
        for (DocumentModel oneChild : children) {
            if (oneChild.getTitle().equals(inTitle)) {
                theChild = oneChild;
                break;
            }
        }

        lastParentPath = ticket.getPathAsString();
        lastTitle = inTitle;

        return theChild;
    }
    
    public DocumentModel getFoundChild() {
        return theChild;
    }

}
