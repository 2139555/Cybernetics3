package com.example.witsonline;

import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
public class BrowseQuizQuestionsTest {

    //isEmpty Mocks
    TextInputLayout isEmptyTextLayout_empty=Mockito.mock(TextInputLayout.class);
    EditText isEmptyEditText_empty=Mockito.mock(EditText.class);
    TextInputLayout isEmptyTextLayout_notempty=Mockito.mock(TextInputLayout.class);;
    EditText isEmptyEditText_notempty=Mockito.mock(EditText.class);

    //invalidMark Mocks
    TextInputLayout invalidMarkLayout_empty=Mockito.mock(TextInputLayout.class);
    EditText invalidMarkEditText_empty=Mockito.mock(EditText.class);
    TextInputLayout invalidMarkLayout_notempty=Mockito.mock(TextInputLayout.class);;
    EditText invalidMarkEditText_notempty=Mockito.mock(EditText.class);
    TextInputLayout invalidMarkLayout_zero=Mockito.mock(TextInputLayout.class);;
    EditText invalidMarkEditText_zero=Mockito.mock(EditText.class);

    //Mocks for is last Item Displaying when Not empty
    RecyclerView recyclerViewTest_NotEmpty= Mockito.mock(RecyclerView.class);
    RecyclerView.Adapter adapter_NotEmpty=Mockito.mock((RecyclerView.Adapter.class));
    LinearLayoutManager linearLayoutManager_NotEmpty=Mockito.mock(LinearLayoutManager.class);

    //Mocks for is last Item Displaying when empty
    RecyclerView recyclerViewTest_Empty= Mockito.mock(RecyclerView.class);
    RecyclerView.Adapter adapter_Empty=Mockito.mock((RecyclerView.Adapter.class));
    LinearLayoutManager linearLayoutManager_Empty=Mockito.mock(LinearLayoutManager.class);
    @Before
    public void init(){

        // setUP for isEmpty tests
        Mockito.when(isEmptyTextLayout_empty.getEditText()).thenReturn(isEmptyEditText_empty);
        Mockito.when(isEmptyTextLayout_notempty.getEditText()).thenReturn(isEmptyEditText_notempty);
        Mockito.when(isEmptyEditText_empty.getText()).thenReturn(new MockEditable(""));
        Mockito.when(isEmptyEditText_notempty.getText()).thenReturn(new MockEditable("123"));

        //set up for invalidMark
        Mockito.when(invalidMarkLayout_empty.getEditText()).thenReturn(invalidMarkEditText_empty);
        Mockito.when(invalidMarkLayout_notempty.getEditText()).thenReturn(invalidMarkEditText_notempty);
        Mockito.when(invalidMarkLayout_zero.getEditText()).thenReturn(invalidMarkEditText_zero);
        Mockito.when(invalidMarkEditText_empty.getText()).thenReturn(new MockEditable(""));
        Mockito.when(invalidMarkEditText_notempty.getText()).thenReturn(new MockEditable("123"));
        Mockito.when(invalidMarkEditText_zero.getText()).thenReturn(new MockEditable("0"));


        //set up for last item displaying when not empty
        Mockito.when(recyclerViewTest_NotEmpty.getAdapter()).thenReturn(adapter_NotEmpty);
        Mockito.when(adapter_NotEmpty.getItemCount()).thenReturn(1);
        Mockito.when(recyclerViewTest_NotEmpty.getLayoutManager()).thenReturn(linearLayoutManager_NotEmpty);

        //set up for last item displaying when empty
        Mockito.when(recyclerViewTest_Empty.getAdapter()).thenReturn(adapter_Empty);
        Mockito.when(adapter_Empty.getItemCount()).thenReturn(1);
        Mockito.when(recyclerViewTest_Empty.getLayoutManager()).thenReturn(linearLayoutManager_Empty);






    }

    @Test
    public void isEmpty_withEmptyInput() {
        BrowseQuizQuestions temp = new BrowseQuizQuestions();
        Boolean output=temp.isEmpty(isEmptyTextLayout_empty);
        assertEquals(true,output);
    }
    @Test
    public void isEmpty_withNonEmptyInput() {
        BrowseQuizQuestions temp = new BrowseQuizQuestions();
        Boolean output=temp.isEmpty(isEmptyTextLayout_notempty);
        assertEquals(false,output);
    }

    @Test
    public void invalidMark_withEmptyInput() {
        BrowseQuizQuestions temp = new BrowseQuizQuestions();
        Boolean output=temp.invalidMark(invalidMarkLayout_empty);
        assertEquals(true,output);
    }
    @Test
    public void invalidMark_withInputLessThan0() {
        BrowseQuizQuestions temp = new BrowseQuizQuestions();
        Boolean output=temp.invalidMark(invalidMarkLayout_zero);
        assertEquals(true,output);
    }
    @Test
    public void invalidMark_withValidInput() {
        BrowseQuizQuestions temp = new BrowseQuizQuestions();
        Boolean output=temp.invalidMark(invalidMarkLayout_notempty);
        assertEquals(false,output);
    }


    @Test
    public void isLastItemDisplaying_Notempty() {
        BrowseQuizQuestions temp = new BrowseQuizQuestions();
        Boolean output=temp.isLastItemDistplaying(recyclerViewTest_NotEmpty);
        assertEquals(true,output);
    }

    @Test
    public void isLastItemDisplaying_empty() {
        BrowseQuizQuestions temp = new BrowseQuizQuestions();
        Boolean output=temp.isLastItemDistplaying(recyclerViewTest_Empty);
        assertEquals(true,output);
    }
}