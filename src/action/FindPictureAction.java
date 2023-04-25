package action;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import model.PictureFile;

public class FindPictureAction {
    private String path;
    public FindPictureAction(String path, TreeView<PictureFile> leafView){
        this.path=path;
        String[] parts = path.split("\\\\");
        parts[0]+="\\";
        TreeItem<PictureFile> currentItem=leafView.getRoot();
        for(String part : parts){
            boolean found=false;
            for(TreeItem<PictureFile> child : currentItem.getChildren()){
                if(child.getValue().toString().equals(part)){
                    currentItem=child;
                    found=true;
                    break;
                }
            }
            if(!found)
                return;
            expandTreeView(currentItem);
        }
    }

    private void expandTreeView(TreeItem<PictureFile> item){
        if(item!=null){
            expandTreeView(item.getParent());
            item.setExpanded(true);
        }
    }
}
