package com.sebulli.fakturama.views.datatable.tree.model;

import java.util.ArrayList;
import java.util.List;

import com.sebulli.fakturama.misc.DocumentType;
import com.sebulli.fakturama.resources.core.Icon;
import com.sebulli.fakturama.views.datatable.tree.ui.TreeObjectType;

/**
 * This class represents a tree object, that can be the parent of an other tree
 * object
 * 
 * @author Gerd Bartelt
 */
public class TreeObject {

    private String name;
    private String command;
    private String toolTip;
    private Icon icon;
    private TreeObject parent;
    private TreeObjectType nodeType;
    private DocumentType docType;
    private List<TreeObject> children = new ArrayList<>();
    private long transactionId = -1;
    private long contactId = -1;
 
    /**
     * Constructor Create a tree object by name
     * 
     * @param name
     *            Name of the tree object
     */
    public TreeObject(String name) {
        this(name, null, null);
    }

    /**
     * Constructor Create a tree object by name and icon
     * 
     * @param name
     *            Name of the tree object
     * @param icon
     *            Icon of the tree object
     */
    public TreeObject(String name, Icon icon) {
        this(name, null, icon);
    }

    /**
     * Constructor Create a tree object by name and icon
     * 
     * @param name
     *            Name of the tree object
     * @param command
     *            of the tree object
     * @param icon
     *            Icon of the tree object
     */
    public TreeObject(String name, String command, Icon icon) {
        this.name = name;
        this.command = command;
        this.icon = icon;
//        nodeType = TreeObjectType.DEFAULT_NODE;
        nodeType = TreeObjectType.LEAF_NODE;
    }

    /**
     * Checks if a certain child exists. The check is at name level.
     * 
     * @param child
     * @return
     */
    public boolean hasChild(TreeObject child) {
        boolean retval = false;
        for (TreeObject treeObject : children) {
            if (treeObject.getFullPathName().equals(child.getFullPathName())) {
                retval = true;
                break;
            }
        }
        return retval;
    }

    public TreeObject findChildWithName(String name) {
        TreeObject retval = null;
        for (TreeObject treeObject : children) {
            if (treeObject.getName().equals(name)) {
                retval = treeObject;
                break;
            }
        }
        return retval;
    }

    /**
     * Returns the tool tip text of the tree object
     * 
     * @return The name
     */
    public String getToolTip() {
        return toolTip;
    }

    /**
     * Returns the name of the tree object
     * 
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the icon of the tree object
     * 
     * @return The icon
     */
    public Icon getIcon() {
        return icon;
    }

    /**
     * Returns the command of the tree object
     * 
     * @return The command
     */
    public String getCommand() {
        return command;
    }

    /**
     * Returns the ID of the transaction
     * 
     * @return Transaction ID
     */
    public long getTransactionId() {
        return this.transactionId;
    }

    /**
     * Returns the ID of the contact
     * 
     * @return Contact ID
     */
    public long getContactId() {
        return this.contactId;
    }

    /**
     * Sets the tool tip text
     * 
     * @param toolTip
     *            the tool tip text
     */
    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }

    /**
     * Sets the Transaction ID
     * 
     * @param transactionId
     *            ID to set
     */
    public void setTransactionId(long transactionId) {
        this.transactionId = transactionId;
    }

    /**
     * Sets the contact ID
     * 
     * @param contactId
     *            ID to set
     */
    public void setContactId(long contactId) {
        this.contactId = contactId;
    }

    /**
     * Sets the name of the tree object
     * 
     * @param name
     *            The name of the tree object
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the parent object
     * 
     * @param parent
     */
    public void setParent(TreeObject parent) {
        this.parent = parent;
    }

    /**
     * Returns the parent object
     * 
     * @return The parent object
     */
    public TreeObject getParent() {
        return parent;
    }

    /**
     * Add a child to the tree object
     * 
     * @param child
     *            The child to add
     */
    public void addChild(TreeObject child) {
        children.add(child);
        child.setParent(this);
    }

    /**
     * Remove a child from the tree object
     * 
     * @param child
     *            to remove
     */
    public void removeChild(TreeObject child) {
        children.remove(child);
        child.setParent(null);
    }

    /**
     * Returns all children
     * 
     * @return Array with all children
     */
    public TreeObject[] getChildren() {
        return children.toArray(new TreeObject[children.size()]);
    }

    /**
     * Returns whether the parent has children or not
     * 
     * @return True, if there are children
     */
    public boolean hasChildren() {
        return !children.isEmpty();
    }

    /**
     * Remove the object's icon
     */
    public void removeIcon() {
        ((TreeObject) this).icon = null;
    }

    /**
     * Remove all children
     */
    public void clear() {
        children.clear();
    }

    /**
     * @return the nodeType
     */
    public TreeObjectType getNodeType() {
        return nodeType;
    }

    /**
     * @param nodeType
     *            the nodeType to set
     */
    public void setNodeType(TreeObjectType nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * @return the docType
     */
    public DocumentType getDocType() {
        return docType;
    }

    /**
     * @param docType the docType to set
     */
    public void setDocType(DocumentType docType) {
        this.docType = docType;
    }

    /**
     * Returns a string representation of the object.
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getName();
    }
    
    public String getFullPathName(boolean omitRootNodeName) {
        // Get the name of the element (this is the rightmost element in path)
        String fullPathName = getName();

        // Root and all have an empty path
        if (getNodeType() == TreeObjectType.ROOT_NODE || getNodeType() == TreeObjectType.ALL_NODE)
            return "";

        TreeObject p = this;
        p = p.getParent();

        //Add the name of all parent elements
        while (p != null && !(getNodeType() == TreeObjectType.ROOT_NODE || getNodeType() == TreeObjectType.ALL_NODE)) {
            if(omitRootNodeName && (p.getNodeType() == TreeObjectType.ROOT_NODE || p.getNodeType() == TreeObjectType.ALL_NODE)) {
                // don't include the root name
            } else {
                fullPathName = p.getName() + "/" + fullPathName;
            }
            p = p.getParent();
        }

        // The full path name
        return fullPathName;
    }

    /**
     * Returns the full path of the tree element. This is the name of the
     * element plus the name of all parent elements, separated by a slash "/"
     * 
     * @return The full path of a tree element
     */
    public String getFullPathName() {
        return getFullPathName(false);
    }

    /**
     * Checks if the given Path is a member of the node's children.
     * 
     * @param checkPath
     * @return
     */
    public TreeObject findNode(String checkPath) {
        TreeObject retval = null;
        
//        // if this path is with a "root" entry as start node
//        if(checkPath.startsWith("//")) {
//            checkPath = checkPath.replaceAll("//", "/");
//        }
        for (TreeObject treeObject : children) {
            if (treeObject.getFullPathName().equals(checkPath)) {
                retval = treeObject;
                break;
            }
        }
        return retval;
    }

}
