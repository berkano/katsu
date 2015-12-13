package ld34.entities;

/**
 * Created by shaun on 13/12/2015.
 */
public class MapTree extends LD34EntityBase {

    @Override
    public void update() {
        Tree tree = new Tree();
        tree.setX(getX());
        tree.setY(getY());
        tree.setRoom(getRoom());
        if (this instanceof TreeLarge) {
            tree.setStage(Tree.Stage.large);
        }
        if (this instanceof TreeMedium) {
            tree.setStage(Tree.Stage.medium);
        }
        if (this instanceof TreeSmall) {
            tree.setStage(Tree.Stage.small);
        }
        if (this instanceof Sapling) {
            tree.setStage(Tree.Stage.sapling);
        }
        getRoom().addNewEntity(tree);
        this.destroy();
    }


}
