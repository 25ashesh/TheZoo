package ashesh_solutions.thezoo3;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.view.View;
import android.net.Uri;
import android.widget.Toast;
import android.util.Log;

public class Zoo extends AppCompatActivity implements View.OnClickListener {


    private ImageView imageView;
    private Button previousButton, nextButton, infoButton;
    int images[] = {R.drawable.bengal_tiger, R.drawable.danphe, R.drawable.rhino, R.drawable.red_panda, R.drawable.salak};
    int currentImage, x;
    MediaPlayer mp;
    SoundPool sp;

    int sound_click, sound_growl, sound_danphe, sound_rhino, sound_panda, sound_salak;
    int num_sounds_loaded;
    boolean sounds_loaded;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle inBundle) {
        super.onCreate(inBundle);
        setContentView(R.layout.activity_zoo);

        imageView = (ImageView) findViewById(R.id.imageView2);
        previousButton = (Button) findViewById(R.id.previousButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        infoButton = (Button) findViewById(R.id.infoButton);

        if (inBundle != null)
            currentImage = inBundle.getInt("currentImage");
        else
            currentImage = 0;

        previousButton.setOnClickListener(this);
        previousButton.setEnabled(false);
        nextButton.setOnClickListener(this);
        imageView.setOnClickListener(this);
        infoButton.setOnClickListener(this);
        num_sounds_loaded = 0;
        sounds_loaded = false;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            sp = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        } else {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            SoundPool sounds = new SoundPool.Builder()
                    .setAudioAttributes(attributes)
                    .build();
        }

        sp.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int mySoundId, int status) {
                num_sounds_loaded++;
                if (num_sounds_loaded == 2)
                    sounds_loaded = true;
            }
        });

        sound_click = sp.load(this, R.raw.click, 1);
        sound_growl = sp.load(this, R.raw.bengal_tiger, 1);
        sound_danphe = sp.load(this, R.raw.danphe, 1);
        sound_rhino = sp.load(this, R.raw.rhino, 1);
        sound_panda = sp.load(this, R.raw.red_panda, 1);
        sound_salak = sp.load(this, R.raw.salak, 1);

    }

    @Override
    protected void onResume() {

        super.onResume();
        imageView.setImageResource(images[currentImage]);

        mp = null;
        mp = MediaPlayer.create(this, R.raw.zoo);
        if (mp != null) {
            mp.setLooping(true);
            mp.start();
        }
        Log.i("onResume", "Saved Image =" + x);

    }

    @Override
    protected void onSaveInstanceState(Bundle outBundle) {
        super.onSaveInstanceState(outBundle);

        outBundle.putInt("currentImage", currentImage);
    }

    @Override
    protected void onPause() {
        if (mp != null) {
            mp.pause();
            mp.release();
            mp = null;
        }
        super.onPause();
        x = currentImage;
        Log.i("onPause", "Current Image =" + currentImage);
    }
@Override
public void onBackPressed(){
    //super.onBackPressed();

    new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Close Application?")
            .setMessage("Confirm to exit the application")
            .setPositiveButton("Yes",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog,int which){
                    finish();
                }
            })
            .setNegativeButton("No",null)
            .show();
}
    //public void onClick(View v){
    //    Intent myIntent = new Intent(Intent.ACTION_VIEW,Uri.parse("http://www.google.com"));
    //    startActivity(myIntent);
    @Override
        public void onClick(View v) {
        previousButton.setEnabled(true);
        switch (v.getId()) {

            case R.id.previousButton:
                if (sounds_loaded)
                    sp.play(sound_click, 1, 1, 0, 0, 1);

                if (currentImage != 0) {//if not first image
                    currentImage = currentImage - 1;
                    imageView.setImageResource(images[currentImage]);
                } else {
                    currentImage = 1;
                }
                break;
            case R.id.nextButton:
                if (sounds_loaded)
                    sp.play(sound_click, 1, 1, 0, 0, 1);

                if (currentImage == images.length - 1) {//if this is the last image
                    imageView.setImageResource(images[images.length - 1]); //pointing to last image
                } else {
                    currentImage = currentImage + 1;
                    imageView.setImageResource(images[currentImage]);
                }
                break;

            case R.id.imageView2:
                if (sounds_loaded)
                    switch (currentImage) {
                        case 0:
                            sp.play(sound_growl, 1, 1, 0, 0, 1);
                            Toast.makeText(this,"growl", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            sp.play(sound_danphe, 1, 1, 0, 0, 1);
                            Toast.makeText(this,"chirp", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            sp.play(sound_rhino, 1, 1, 0, 0, 1);
                            Toast.makeText(this,"bellow", Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            sp.play(sound_panda, 1, 1, 0, 0, 1);
                            Toast.makeText(this,"huff", Toast.LENGTH_SHORT).show();
                            break;
                        case 4:
                            sp.play(sound_salak, 1, 1, 0, 0, 1);
                            Toast.makeText(this,"squeak", Toast.LENGTH_SHORT).show();
                            break;
                    }
                break;

            case R.id.infoButton:
                switch (currentImage) {
                    case 0:
                        Intent myIntent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Bengal_tiger"));
                        startActivity(myIntent1);
                        break;
                    case 1:
                        Intent myIntent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Himalayan_monal"));
                        startActivity(myIntent2);
                        break;
                    case 2:
                        Intent myIntent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.worldwildlife.org/species/greater-one-horned-rhino"));
                        startActivity(myIntent3);
                        break;
                    case 3:
                        Intent myIntent4 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.worldwildlife.org/species/red-panda"));
                        startActivity(myIntent4);
                        break;
                    case 4:
                        Intent myIntent5 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://en.wikipedia.org/wiki/Chinese_pangolin"));
                        startActivity(myIntent5);
                        break;

                }break;

        }
    }
}
