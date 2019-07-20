package io.github.austindewitt13.rot;

import android.app.Application;
import androidx.room.Database;
import com.facebook.stetho.Stetho;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import io.github.austindewitt13.rot.model.Alarm;
import io.github.austindewitt13.rot.model.Event;
import io.github.austindewitt13.rot.service.GoogleSignInService;

public class AlarmEventApp extends Application {


  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    GoogleSignInService.setContext(this);
  }

}
