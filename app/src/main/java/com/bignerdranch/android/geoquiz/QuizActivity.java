package com.bignerdranch.android.geoquiz;

// GeoQuiz sample application from "Android Programming" book

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    // Key names for app state storing
    private static final String KEY_INDEX = "index";
    private static final String KEY_ANSWERED = "answered";
    private static final String KEY_ANSWER_COUNT = "answer_count";
    private static final String KEY_RIGHT_ANSWERS = "right_answers";

    private TextView mQuestionTextView;

    // Questions and answers
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };
    private  boolean[] answered = new boolean[mQuestionBank.length];

    // Answer buttons
    private Button mTrueButton;
    private Button mFalseButton;

    // Current question index
    private int mCurrentIndex = 0;

    // Answer counters
    private int answerCount = 0;
    private int rightAnswers = 0;

    // Run point
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Load app state if any exists
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            answered = savedInstanceState.getBooleanArray(KEY_ANSWERED);
            answerCount = savedInstanceState.getInt(KEY_ANSWER_COUNT);
            rightAnswers = savedInstanceState.getInt(KEY_RIGHT_ANSWERS);
        }

        // Question text
        mQuestionTextView = findViewById(R.id.question_text_view);

        // True answer button
        mTrueButton = findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        // False answer button
        mFalseButton = findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        // Next question button
        ImageButton mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex < mQuestionBank.length - 1) {
                    mCurrentIndex++;
                    updateQuestion();
                }
            }
        });

        // Prev question button
        ImageButton mPrevButton = findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCurrentIndex > 0) {
                    mCurrentIndex--;
                    updateQuestion();
                }
            }
        });

        updateQuestion();

    } // protected void onCreate(Bundle savedInstanceState) {

    // Saving app state for reinitialization on device rotating
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBooleanArray(KEY_ANSWERED, answered);
        savedInstanceState.putInt(KEY_ANSWER_COUNT, answerCount);
        savedInstanceState.putInt(KEY_RIGHT_ANSWERS, rightAnswers);
    }

    // Set question text
    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
        mTrueButton.setEnabled(!answered[mCurrentIndex]);
        mFalseButton.setEnabled(!answered[mCurrentIndex]);
    }

    // Check answer and info user about result
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId;
        if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
            rightAnswers++;
        }
        else
            messageResId = R.string.incorrect_toast;
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
        answered[mCurrentIndex] = true;
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
        answerCount++;
        if (answerCount == mQuestionBank.length)
            Toast.makeText(this,
                    "Rights answers " + rightAnswers + "/" + answerCount,
                    Toast.LENGTH_SHORT).show();
    } // private void checkAnswer(boolean userPressedTrue) {

} // public class QuizActivity extends AppCompatActivity {
