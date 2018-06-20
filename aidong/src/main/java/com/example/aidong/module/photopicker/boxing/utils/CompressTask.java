/*
 *  Copyright (C) 2017 Bilibili
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.aidong.module.photopicker.boxing.utils;

import android.content.Context;

import com.example.aidong .module.photopicker.boxing.model.entity.impl.ImageMedia;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * A compress task for {@link ImageMedia}
 * @author ChenSL
 */
public class CompressTask {
    public static boolean compress(Context context, final ImageMedia image) {
        return compress(new ImageCompressor(context), image, ImageCompressor.MAX_LIMIT_SIZE_LONG);
    }

    public static boolean compress(final ImageCompressor imageCompressor, final ImageMedia image, final long maxSize) {
        if (imageCompressor == null || image == null || maxSize <= 0) {
            return false;
        }
        FutureTask<Boolean> task = BoxingExecutor.getInstance().runWorker(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                final String path = image.getPath();
                File compressSaveFile = imageCompressor.getCompressOutFile(path);
                File needCompressFile = new File(path);
                if (isLegalFile(compressSaveFile)) {
                    image.setCompressPath(compressSaveFile.getAbsolutePath());
                    return true;
                }
                if (!isLegalFile(needCompressFile)) {
                    return false;
                } else if (image.getSize() < maxSize) {
                    image.setCompressPath(path);
                    return true;
                } else {
                    try {
                        File result = imageCompressor.compress(needCompressFile);
                        boolean suc = isLegalFile(result);
                        image.setCompressPath(suc ? result.getAbsolutePath() : null);
                        return suc;
                    } catch (IOException | OutOfMemoryError | NullPointerException | IllegalArgumentException e) {
                        image.setCompressPath(null);
                        BoxingLog.d("image compress fail!");
                    }
                }
                return false;
            }
        });
        try {
            return task != null && task.get();
        } catch (InterruptedException | ExecutionException ignore) {
            return false;
        }
    }

    private static boolean isLegalFile(File file) {
        return file != null && file.exists() && file.isFile() && file.length() > 0 && file.canRead();
    }
}