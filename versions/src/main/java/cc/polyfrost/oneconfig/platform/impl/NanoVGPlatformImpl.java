/*
 * This file is part of OneConfig.
 * OneConfig - Next Generation Config Library for Minecraft: Java Edition
 * Copyright (C) 2021, 2022 Polyfrost.
 *   <https://polyfrost.cc> <https://github.com/Polyfrost/>
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 *   OneConfig is licensed under the terms of version 3 of the GNU Lesser
 * General Public License as published by the Free Software Foundation, AND
 * under the Additional Terms Applicable to OneConfig, as published by Polyfrost,
 * either version 1.0 of the Additional Terms, or (at your option) any later
 * version.
 *
 *   This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 * License.  If not, see <https://www.gnu.org/licenses/>. You should
 * have also received a copy of the Additional Terms Applicable
 * to OneConfig, as published by Polyfrost. If not, see
 * <https://polyfrost.cc/legal/oneconfig/additional-terms>
 */

package cc.polyfrost.oneconfig.platform.impl;

import cc.polyfrost.oneconfig.platform.NanoVGPlatform;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.nanovg.NVGLUFramebuffer;
import org.lwjgl.nanovg.NanoVGGL2;

public class NanoVGPlatformImpl implements NanoVGPlatform {
    @Override
    public int nnvglCreateImageFromHandle(long var0, int var2, int var3, int var4, int var5) {
        return NanoVGGL2.nnvglCreateImageFromHandle(var0, var2, var3, var4, var5);
    }

    @Override
    public int nvglCreateImageFromHandle(long ctx, int textureId, int w, int h, int flags) {
        return NanoVGGL2.nvglCreateImageFromHandle(ctx, textureId, w, h, flags);
    }

    @Override
    public int nnvglImageHandle(long var0, int var2) {
        return NanoVGGL2.nnvglImageHandle(var0, var2);
    }

    @Override
    public int nvglImageHandle(long ctx, int image) {
        return NanoVGGL2.nvglImageHandle(ctx, image);
    }

    @Override
    public long nnvgCreate(int var0) {
        return NanoVGGL2.nnvgCreate(var0);
    }

    @Override
    public long nvgCreate(int flags) {
        return NanoVGGL2.nvgCreate(flags);
    }

    @Override
    public void nnvgDelete(long var0) {
        NanoVGGL2.nnvgDelete(var0);
    }

    @Override
    public void nvgDelete(long ctx) {
        NanoVGGL2.nvgDelete(ctx);
    }

    @Override
    public long nnvgluCreateFramebuffer(long var0, int var2, int var3, int var4) {
        return NanoVGGL2.nnvgluCreateFramebuffer(var0, var2, var3, var4);
    }

    @Nullable
    @Override
    public NVGLUFramebuffer nvgluCreateFramebuffer(long ctx, int w, int h, int imageFlags) {
        return NanoVGGL2.nvgluCreateFramebuffer(ctx, w, h, imageFlags);
    }

    @Override
    public void nnvgluBindFramebuffer(long var0, long var2) {
        NanoVGGL2.nnvgluBindFramebuffer(var0, var2);
    }

    @Override
    public void nvgluBindFramebuffer(long ctx, @Nullable NVGLUFramebuffer fb) {
        NanoVGGL2.nvgluBindFramebuffer(ctx, fb);
    }

    @Override
    public void nnvgluDeleteFramebuffer(long var0, long var2) {
        NanoVGGL2.nnvgluDeleteFramebuffer(var0, var2);
    }

    @Override
    public void nvgluDeleteFramebuffer(long ctx, NVGLUFramebuffer fb) {
        NanoVGGL2.nvgluDeleteFramebuffer(ctx, fb);
    }

    @Override
    public void triggerStaticInitialization() {
        try {
            Class.forName(NanoVGGL2.class.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
