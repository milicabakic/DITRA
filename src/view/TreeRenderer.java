package view;

import java.awt.Component;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import resource.enums.ConstraintType;
import resource.implementation.Attribute;
import resource.implementation.AttributeConstraint;
import resource.implementation.Entity;
import resource.implementation.InformationResource;


public class TreeRenderer extends DefaultTreeCellRenderer {
	
public TreeRenderer() {
		
	}

	  public Component getTreeCellRendererComponent(
              JTree tree,            //stablo koje se prikazuje
              Object value,          //stavka koja se iscrtava  
              boolean sel,           //da li je stavka selektovana
              boolean expanded,
              boolean leaf,          //da li je list
              int row,
              boolean hasFocus) {    //da li je u fokusu
              super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row,hasFocus);
                  
             
             if (value instanceof Entity ) {
                 URL imageURL = getClass().getResource("gui_icons/table1.png");
                 Icon icon = null;
                 if (imageURL != null)                       
                     icon = new ImageIcon(imageURL);
                 setIcon(icon);
 
             } else if (value instanceof InformationResource ) {
            	 URL imageURL=getClass().getResource("gui_icons/file.jpg");
                 Icon icon = null;
                 if (imageURL != null)                       
                     icon = new ImageIcon(imageURL);
                 setIcon(icon);
                   
            } else if (value instanceof Attribute ) {
           	     URL imageURL=getClass().getResource("gui_icons/attribute.png");
                 Icon icon = null;
                 if (imageURL != null)                       
                 icon = new ImageIcon(imageURL);
                 setIcon(icon);
               
            } else if (value instanceof AttributeConstraint) {
            	AttributeConstraint ac = (AttributeConstraint) value;
          	    URL imageURL=null;
          	    if (ac.getConstraintType() == ConstraintType.PRIMARY_KEY) imageURL=getClass().getResource("gui_icons/primary.png");
          	    else if(ac.getConstraintType() == ConstraintType.FOREIGN_KEY) imageURL=getClass().getResource("gui_icons/imported.jpg");
          	  else if(ac.getConstraintType() == ConstraintType.NOT_NULL) imageURL=getClass().getResource("gui_icons/notnull.png");
                Icon icon = null;
                if (imageURL != null)                       
                icon = new ImageIcon(imageURL);
                setIcon(icon);
              
           }  
            return this;
}

}
