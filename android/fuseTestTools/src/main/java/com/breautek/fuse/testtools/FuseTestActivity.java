
/*
Copyright 2023 Breautek

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.breautek.fuse.testtools;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import com.breautek.fuse.FuseContext;
import com.breautek.fuse.FuseFragment;

public class FuseTestActivity extends AppCompatActivity {
    private FuseFragment $fuse;
    private FuseContext.IReadyCallback $callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        FrameLayout layout = new FrameLayout(this);
        layout.setId(View.generateViewId());
        layout.setLayoutParams(new FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        ));

        setContentView(layout);

        $fuse = FuseFragment.newInstance(() -> {
            FuseContext fuseContext = $fuse.getFuseContext();
            _registerPlugins(fuseContext);
            $callback.onReady();
        });
        getSupportFragmentManager().beginTransaction().add(layout.getId(), $fuse).commit();
    }

    public void setOnReadyCallback(FuseContext.IReadyCallback callback) {
        $callback = callback;
    }

    protected void _registerPlugins(FuseContext context) {}

    public FuseContext getFuseContext() {
        return $fuse.getFuseContext();
    }
}
