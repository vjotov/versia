/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.jotov.versia.command;

/**
 *
 * @author Vladimir Jotov
 */
public class CommandFactory {
    public enum CmdCode {ATTACH_WORK_ITEM, CREATE_PRODUCT, CREATE_RELEASE, CREATE_VERSIONED_OBJECT, CREATE_WORK_ITEM,CREATE_WORKSPACE,
        DELETE_PRODUCT, DELETE_RELEASE, DELETE_VERSIONED_OBJECT, DELETE_WORKSPACE, DETACH_WORK_ITEM, GET_PRODUCTS, GET_RELEASE_LIST,
        GET_VERSIONED_OBJECT_LIST, GET_WORK_ITEM_LIST, GET_WORKSPACE_LIST, PUBLISH_VERSIONED_BJECT, PUT_BACK_VERSIONED_OBJECT, SAVE_VERSIONED_OBJECT,
        UPDATE_PRODUCT, UPDATE_RELEASE, UPDATE_WORKSPACE, VIEW_VERSIONED_OBJECT_DISTRIBUTION, VIEW_VERSIONED_OBJECT_HISTORY, GET_USER_LIST,
        CREATE_USER, UPDATE_PERMITION};
    public ICommand createCommand (CmdCode commandCode) {
        
        ICommand cmd;
        switch (commandCode){
            case ATTACH_WORK_ITEM: cmd = new AttachWorkItem(); break;
            case CREATE_PRODUCT: cmd = new CreateProduct(); break;
            case CREATE_RELEASE: cmd = new CreateRelease(); break;
            case CREATE_VERSIONED_OBJECT: cmd = new CreateVersionedObject(); break;
            case CREATE_WORK_ITEM: cmd = new CreateWorkItem(); break;
            case CREATE_WORKSPACE: cmd = new CreateWorkspace(); break;
            case DELETE_PRODUCT: cmd = new DeleteProduct(); break;
            case DELETE_RELEASE: cmd = new DeleteRelease(); break;
            case DELETE_VERSIONED_OBJECT: cmd = new DeleteVersionedObject(); break;
            case DELETE_WORKSPACE: cmd = new DeleteWorkspace(); break;
            case DETACH_WORK_ITEM: cmd = new DetachWorkItem(); break;
            case GET_PRODUCTS: cmd = new GetProducts(); break;
            case GET_RELEASE_LIST: cmd = new GetReleaseList(); break;
            case GET_VERSIONED_OBJECT_LIST: cmd = new GetVersionedObjectList(); break;
            case GET_WORK_ITEM_LIST: cmd = new GetWorkItemList(); break;
            case GET_WORKSPACE_LIST: cmd = new GetWorkspaceList(); break;
            case PUBLISH_VERSIONED_BJECT: cmd = new PublishVersionedObject(); break;
            case PUT_BACK_VERSIONED_OBJECT: cmd = new PutBackVersionedObject(); break;
            case SAVE_VERSIONED_OBJECT: cmd = new SaveVersionedObject(); break;
            case UPDATE_PRODUCT: cmd = new UpdateProduct(); break;
            case UPDATE_RELEASE: cmd = new UpdateRelease(); break;
            case UPDATE_WORKSPACE: cmd = new UpdateWorkspace(); break;
            case VIEW_VERSIONED_OBJECT_DISTRIBUTION: cmd = new ViewVersionedObjectDistribution(); break;
            case VIEW_VERSIONED_OBJECT_HISTORY: cmd = new ViewVersionedObjectHistory(); break;
            case GET_USER_LIST: cmd = new GetUserList(); break;
            case CREATE_USER: cmd = new CreateUser(); break;
            case UPDATE_PERMITION: cmd = new UpdatePermition(); break;
            default: cmd = null;
        }
        return cmd;
    }
}
