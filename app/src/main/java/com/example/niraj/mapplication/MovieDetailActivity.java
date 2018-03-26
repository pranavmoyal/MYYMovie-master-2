package com.example.niraj.mapplication;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class MovieDetailActivity extends AppCompatActivity {
    TextView mTvSOverview;
    TextView mTvSTitle;
    TextView mTvSRel_date;
    TextView mTvSOriginal_title;
    TextView mTvSLanguage;
    ImageView mIvSivBigImage;
    String mImageUrl;
    float oldXvalue,oldYvalue;
    FloatingActionButton FAB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        if (getIntent() != null) {
            mImageUrl = getIntent().getStringExtra("imageUri");
        }

        Button b=(Button) findViewById(R.id.txt);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MovieDetailActivity.this, YouTubeVideoActivity.class);
                startActivity(intent);
            }
        });

        final Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar();

        FAB = (FloatingActionButton) findViewById(R.id.fab);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareIt();
            }
        });

        final DetailsActivity detail = getIntent().getParcelableExtra("detail");

        mTvSLanguage = (TextView) findViewById(R.id.Language);

        mTvSOriginal_title = (TextView) findViewById(R.id.original_title);

        mIvSivBigImage = (ImageView) findViewById(R.id.ivBigImage);

        mTvSOverview = (TextView) findViewById(R.id.overview);

        mTvSTitle = (TextView) findViewById(R.id.title);

        mTvSRel_date = (TextView) findViewById(R.id.Rel_date);

        if (detail != null) {
            mTvSOverview.setText(String.format("Overview:%s", detail.mSOverview));
            mTvSTitle.setText(String.format("Title:%s", detail.mSTitle));
            mTvSRel_date.setText(String.format("Rel_date:%s", detail.mSRel_date));
            mTvSOriginal_title.setText(String.format("Original_title:%s", detail.mSOriginal_Title));
            mTvSLanguage.setText(String.format("Language:%s", detail.mSLanguage));

        }

        Picasso.with(this)
                .load(mImageUrl).resize(500,400)
                .into(mIvSivBigImage);

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        toolbar.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                assert detail != null;
                toolbar.setTitle(detail.mSTitle);
            }

            @Override
            public void onViewDetachedFromWindow(View v) {

            }
        });

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ImageView imageView=(ImageView) findViewById(R.id.background_one);
        final ImageView imageView2=(ImageView) findViewById(R.id.background_two);
        final ImageView imageView3=(ImageView) findViewById(R.id.background_three);

        final ValueAnimator animator = ValueAnimator.ofFloat(0.0f, 3.0f);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(10000L);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                final float progress = (float) animation.getAnimatedValue();
                final float width = imageView.getWidth();
                final float width1=imageView2.getWidth();
                final float width2=imageView3.getWidth();
                final float translationX = width * progress;
                final float translationX1 = width1 * progress;
                final float translationX2 = width2 * progress;
                imageView.setTranslationX(translationX);
                imageView2.setTranslationX(translationX1);
                imageView3.setTranslationX(translationX2);
            }
        });
        animator.start();

        Picasso.with(this)
                .load(mImageUrl)
                .into(imageView);
        Picasso.with(this)
                .load(mImageUrl)
                .into(imageView2);
        Picasso.with(this)
                .load(mImageUrl)
                .into(imageView3);

        showSideNavigationPrompt(FAB);

    }

    public void showSideNavigationPrompt(View view)
    {
        try {
            final MaterialTapTargetPrompt.Builder tapTargetPromptBuilder = new MaterialTapTargetPrompt.Builder(this)
                    .setPrimaryText("Share Everything")
                    .setSecondaryText("You can share all MOVIE details")
                    .setAnimationInterpolator(new FastOutSlowInInterpolator())
                    .setMaxTextWidth(R.dimen.tap_target_menu_max_width)
                    .setTarget(FAB);
            /*final Toolbar tb = this.findViewById(R.id.toolbar);
            tapTargetPromptBuilder.setTarget(tb.getChildAt(1));

            tapTargetPromptBuilder.setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
            {
                @Override
                public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state)
                {
                    if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED)
                    {
                        //Do something such as storing a value so that this prompt is never shown again
                    }
                }
            });*/
            tapTargetPromptBuilder.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void shareIt() {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "https://www.themoviedb.org");
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void next(View view) {
    }

}







