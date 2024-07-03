package me.anjoismysign.anjo.swing.components;

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public class AnjoFileRepositoryPanel extends JPanel {
    private final Consumer<File> onDrop;

    public AnjoFileRepositoryPanel(LayoutManager layout,
                                   @NotNull Consumer<File> onDrop) {
        super(layout);
        Objects.requireNonNull(onDrop, "'onDrop' cannot be null");
        this.onDrop = onDrop;
        setupDropTarget();
    }

    public AnjoFileRepositoryPanel(@NotNull Consumer<File> onDrop) {
        Objects.requireNonNull(onDrop, "'onDrop' cannot be null");
        this.onDrop = onDrop;
        setupDropTarget();
    }

    private void setupDropTarget() {
        new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetAdapter() {
            @Override
            public void drop(DropTargetDropEvent event) {
                try {
                    event.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                    List<File> files = (List<File>) event.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);
                    handleFileDrop(files);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
    }

    private void handleFileDrop(List<File> files) {
        for (File file : files) {
            onDrop.accept(file);
        }
    }
}
